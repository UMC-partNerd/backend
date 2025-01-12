package com.partnerd.service.collabPostService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ClubMemberHandler;
import com.partnerd.apiPaylaod.exception.handler.EventTypeHandler;
import com.partnerd.converter.collabPostConverter.CollabPostConverter;
import com.partnerd.converter.collabPostConverter.CollapPostCategoryConverter;
import com.partnerd.domain.Category;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.ContactMethod;
import com.partnerd.domain.mapping.CollabPostCategory;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.repository.collabPostRepository.EventTypeRepository;
import com.partnerd.service.categoryService.CategoryService;
import com.partnerd.service.collabPostService.CollabPostCommandService;
import com.partnerd.web.dto.collabDTO.CollabPostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollabPostCommandServiceImpl implements CollabPostCommandService {

    private final CollabPostRepository collabPostRepository;
    private final CategoryService categoryService;
    private final EventTypeRepository eventTypeRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public CollabPost addCollabPost(CollabPostRequestDTO.addCollabPostDTO requestDTO) {

        CollabPost collabPost = CollabPostConverter.toCollabPost(requestDTO);
        // 카테고리 객체 찾아서 리스트로 만들기
        List<Category> categoryList =
                requestDTO.getCategoryIds().stream()
                        .map(categoryId -> categoryService.findCategoryById(categoryId.longValue()))
                        .collect(Collectors.toList());



        // CollabPostCategory 리스트를 컨버터를 통해서 빌더로 만들어 생성하고 양방향 관계 설정
        List<CollabPostCategory> collabPostCategoryList = CollapPostCategoryConverter.
                toCollabPostCategoryList(categoryList);
        collabPostCategoryList.forEach(collabPostCategory -> {collabPostCategory.setCollabPost(collabPost);});


        // ContactMethod 추가
        if (requestDTO.getContactMethod() != null) {
            List<ContactMethod> contactMethods = requestDTO.getContactMethod().stream()
                    .map(contactMethodDTO -> {
                        ContactMethod contactMethod = ContactMethod.builder()
                                .contact_type(contactMethodDTO.getContactType())
                                .contact_url(contactMethodDTO.getContactUrl())
                                .build();
                        contactMethod.setCollabPost(collabPost);
                        return contactMethod;
                    })
                    .collect(Collectors.toList());
        }
        // 팀 멤버 하드 코딩
        collabPost.setClubMember(clubMemberRepository.findById(1L).orElseThrow(() ->
                new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND)));
        collabPost.setEventType(eventTypeRepository.findById(requestDTO.getEventTypeId()).orElseThrow(() ->
                new EventTypeHandler(ErrorStatus.EVENT_TYPE_NOT_FOUND)));

        return collabPostRepository.save(collabPost);
    }

    @Override
    public CollabPost modifyCollabPost(CollabPostRequestDTO.modifyCollabPostDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteCollabPost(Long collabPostId) {

    }
}
