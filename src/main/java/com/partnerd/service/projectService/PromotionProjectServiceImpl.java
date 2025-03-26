package com.partnerd.service.projectService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.PromotionProjectHandler;
import com.partnerd.converter.projectConverter.PromotionProjectMemberConverter;
import com.partnerd.converter.projectConverter.PromotionProjectConverter;
import com.partnerd.mongoRepository.domain.ContactMethod;
import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.mongoRepository.domain.PromotionProject;
import com.partnerd.mongoRepository.domain.PromotionProjectImage;
import com.partnerd.mongoRepository.domain.enums.ImageType;
import com.partnerd.mongoRepository.domain.mapping.ProjectVote;
import com.partnerd.mongoRepository.domain.mapping.PromotionProjectMember;
import com.partnerd.repository.projectRepository.ProjectVoteRepository;
import com.partnerd.repository.projectRepository.PromotionProjectMemberRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionProjectServiceImpl implements PromotionProjectService {

    private final PromotionProjectRepository promotionProjectRepository;
    private final PromotionProjectMemberRepository promotionProjectMemberRepository;
    private final MemberRepository memberRepository;
    private final ProjectVoteRepository projectVoteRepository;

    // 프로젝트 홍보글 생성
    @Override
    public PromotionProject addPromotionProject(Long memberId, PromotionProjectRequestDTO.CreatePromotionProjectDTO request) {
        PromotionProject newPromotionProject = PromotionProjectConverter.toPromotionProject(request);

        // 작성자
        newPromotionProject.setMember(memberRepository.findById(memberId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND)));


        Set<Member> memberList = request.getPromotionProjectMember().stream()
                .map(teamMemberId -> memberRepository.findById(teamMemberId)
                        .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_MEMBER_ID_NOT_FOUND)))
                .collect(Collectors.toSet());

        Set<PromotionProjectMember> promotionProjectMemberList = PromotionProjectMemberConverter.toPromotionProjectMemberList(memberList);

        promotionProjectMemberList.forEach(promotionProjectMember -> {promotionProjectMember.setPromotionProject(newPromotionProject);});

        // 대표 이미지 저장
        PromotionProjectImage thumbnailImg = PromotionProjectImage.builder()
                .keyName(request.getThumbnailKeyName())
                .imageType(ImageType.THUMBNAIL)
                .build();
        thumbnailImg.setPromotionProject(newPromotionProject);

        // 프로젝트 이미지 등록
        request.getProjectImgKeyNameList().forEach(keyName -> {
            PromotionProjectImage promotionProjectImage = PromotionProjectImage.builder()
                    .keyName(keyName)
                    .imageType(ImageType.INTRO)
                    .build();
            promotionProjectImage.setPromotionProject(newPromotionProject);
        });

        // 컨택트 방식
        if (request.getContactMethod() != null) {
            Set<ContactMethod> contactMethods = request.getContactMethod().stream()
                    .map(contactMethodDTO -> {
                        ContactMethod contactMethod = ContactMethod.builder()
                                .contactType(contactMethodDTO.getContactType())
                                .contactUrl(contactMethodDTO.getContactUrl())
                                .build();
                        contactMethod.setPromotionProject(newPromotionProject);
                        return contactMethod;
                    })
                    .collect(Collectors.toSet());

            newPromotionProject.setContactMethodList(contactMethods);
        }

        return promotionProjectRepository.save(newPromotionProject);
    }

    // 프로젝트 홍보글 수정
    @Override
    @Transactional
    public PromotionProject updatePromotionProject(Long memberId, PromotionProjectRequestDTO.UpdatePromotionProjectDTO request, Long promotionProjectId) {

        PromotionProject existingPromotionProject = promotionProjectRepository.findById(promotionProjectId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));

        // 작성자 검증
        if (!existingPromotionProject.getMember().getId().equals(memberId))
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_AUTHOR);

        existingPromotionProject.setTitle(request.getTitle());
        existingPromotionProject.setIntro(request.getInfo());
        existingPromotionProject.setDescription(request.getDescription());

        promotionProjectMemberRepository.deleteByPromotionProject(existingPromotionProject);

        Set<Member> memberList = request.getPromotionProjectMember().stream()
                .map(teamMemberId -> memberRepository.findById(teamMemberId)
                        .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_MEMBER_ID_NOT_FOUND)))
                .collect(Collectors.toSet());

        Set<PromotionProjectMember> newMembers = PromotionProjectMemberConverter.toPromotionProjectMemberList(memberList);
        newMembers.forEach(promotionProjectMember -> {promotionProjectMember.setPromotionProject(existingPromotionProject);});

        existingPromotionProject.setPromotionProjectMemberList(newMembers);

        existingPromotionProject.getPromotionProjectImageList().clear();

        // 대표 이미지 저장
        PromotionProjectImage thumbnailImg = PromotionProjectImage.builder()
                .keyName(request.getThumbnailKeyName())
                .imageType(ImageType.THUMBNAIL)
                .build();
        thumbnailImg.setPromotionProject(existingPromotionProject);

        // 프로젝트 이미지 등록
        request.getProjectImgKeyNameList().forEach(keyName -> {
            PromotionProjectImage promotionProjectImage = PromotionProjectImage.builder()
                    .keyName(keyName)
                    .imageType(ImageType.INTRO)
                    .build();
            promotionProjectImage.setPromotionProject(existingPromotionProject);
        });

        // 컨텍트 방식
        if (request.getContactMethod() != null) {
            existingPromotionProject.getContactMethodList().clear();

            request.getContactMethod().forEach(contactMethodDTO -> {
                ContactMethod contactMethod = ContactMethod.builder()
                        .contactType(contactMethodDTO.getContactType())
                        .contactUrl(contactMethodDTO.getContactUrl())
                        .promotionProject(existingPromotionProject)
                        .build();
                existingPromotionProject.getContactMethodList().add(contactMethod);
            });

        }
        return promotionProjectRepository.save(existingPromotionProject);
    }

    // 프로젝트 홍보글 삭제
    public void deletePromotionProject(Long memberId, Long promotionProjectId) {
        PromotionProject existingPromotionProject = promotionProjectRepository.findById(promotionProjectId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));

        // 작성자 검증
        if (!existingPromotionProject.getMember().getId().equals(memberId))
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_AUTHOR);

        promotionProjectRepository.deleteById(existingPromotionProject.getId());
    }

    // 프로젝트 홍보글 모아보기 (인기순/최신순)
    @Override
    @Transactional(readOnly = true)
    public Page<PromotionProject> getPromotionProjectList(Integer page, Integer sort){
        return promotionProjectRepository.getPromotionProjectList(page, sort);
    }

    // 프로젝트 홍보글 모아보기 (인기 top3)
    @Override
    @Transactional(readOnly = true)
    public List<PromotionProject> getPromotionProjectTop3(){
        return promotionProjectRepository.getPromotionProjectTop3();
    }

    // 프로젝트 홍보 모아보기 (검색)
    @Override
    @Transactional(readOnly = true)
    public Page<PromotionProject> getPromotionProjectSearchList(Integer page, String keyword){
        return promotionProjectRepository.getPromotionProjectSearchList(page, keyword);
    }


    // 프로젝트 모집글 상세페이지 조회
    @Override
    public PromotionProjectResponseDTO.PromotionProjectDetailDTO getPromotionProject(Long memberId, Long promotionProjectId){
        PromotionProject promotionProject = promotionProjectRepository.findPromotionProjectDetails(promotionProjectId);

        if (promotionProject == null){
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_ID_NOT_FOUND);
        }

        
        // 투표하기(응원하기) 여부
        boolean isVoted = false;

        if (memberId != null) {
            isVoted = projectVoteRepository.existsByMemberIdAndPromotionProjectId(memberId, promotionProjectId);
        }

        return PromotionProjectConverter.toPromotionProjectDetailDTO(promotionProject, isVoted);
    }

    // 프로젝트 홍보 투표하기
    @Override
    public void projectVotes(Long memberId, Long promotionProjectId){

        boolean exists = projectVoteRepository.existsByMemberIdAndPromotionProjectId(memberId, promotionProjectId);

        PromotionProject promotionProject = promotionProjectRepository.findById(promotionProjectId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_COMMENT_NOT_FOUND));
        if (exists) {
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_ALREADY_VOTE);
        } else {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));
            ProjectVote projectVote = ProjectVote.builder()
                    .member(member)
                    .promotionProject(promotionProject)
                    .build();
            projectVoteRepository.save(projectVote);
            promotionProject.setVote(promotionProject.getVote() + 1);
        }
        promotionProjectRepository.save(promotionProject);
    }

    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기
    @Override
    @Transactional(readOnly=true)
    public List<PromotionProject> getMyPromotionProjects(Long memberId) {
        return promotionProjectRepository.findPromotionProjectsByMemberId(memberId);
    }

    // 마이페이지(퍼스널페이지) - 내가 쓴 프로젝트 홍보글 모아보기
    @Override
    @Transactional(readOnly = true)
    public Page<PromotionProject> getPersonalPromotionProjectList(Integer page, Long memberId){
        return promotionProjectRepository.getPersonalPromotionProjectList(page, memberId);
    }
}
