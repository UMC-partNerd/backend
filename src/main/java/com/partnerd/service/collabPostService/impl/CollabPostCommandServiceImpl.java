package com.partnerd.service.collabPostService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CategoryHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubMemberHandler;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.apiPaylaod.exception.handler.EventTypeHandler;
import com.partnerd.converter.collabPostConverter.CollabPostConverter;
import com.partnerd.converter.collabPostConverter.CollapPostCategoryConverter;
import com.partnerd.domain.*;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.mapping.CollabPostCategory;
import com.partnerd.repository.categoryRepository.CategoryRepository;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.collabPostRepository.CollabPostImgRepository;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.repository.collabPostRepository.EventTypeRepository;
import com.partnerd.service.categoryService.CategoryService;
import com.partnerd.service.collabPostService.CollabPostCommandService;
import com.partnerd.service.s3Service.S3Service;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.ErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollabPostCommandServiceImpl implements CollabPostCommandService {

    private final CollabPostRepository collabPostRepository;
    private final EventTypeRepository eventTypeRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final CategoryRepository categoryRepository;
    private final CollabPostImgRepository collabPostImgRepository;


    @Override
    @Transactional
    public CollabPost addCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        CollabPost collabPost = CollabPostConverter.toCollabPost(requestDTO);
        // 카테고리 객체 찾아서 리스트로 만들기
        List<Category> categoryList =
                requestDTO.getCategoryIds().stream()
                        .map(categoryId ->{
                            Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                                    new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
                            return category;
                        })
                        .collect(Collectors.toList());

        // CollabPostCategory 리스트를 컨버터를 통해서 빌더로 만들어 생성하고 양방향 관계 설정
        List<CollabPostCategory> collabPostCategoryList = CollapPostCategoryConverter.
                toCollabPostCategoryList(categoryList);
        collabPostCategoryList.forEach(collabPostCategory -> { collabPostCategory.setCollabPost(collabPost); });

        // ContactMethod 추가
        if (requestDTO.getContactMethodDTOList() != null) {
             requestDTO.getContactMethodDTOList().stream()
                    .map(contactMethodDTO -> {
                        ContactMethod contactMethod = ContactMethod.builder()
                                .contactType(contactMethodDTO.getContactType())
                                .contactUrl(contactMethodDTO.getContactUrl())
                                .build();
                        contactMethod.setCollabPost(collabPost);
                        return contactMethod;});
        }


        // 이미지 업로드
        CollabPostImg bannerImg = CollabPostImg.builder() // banner
                .keyName(requestDTO.getBannerKeyName())
                .imageType(ImageType.BANNER)
                .build();
        bannerImg.setCollabPost(collabPost);

        CollabPostImg mainImg = CollabPostImg.builder() // main
                .keyName(requestDTO.getMainKeyName())
                .imageType(ImageType.MAIN)
                .build();
        mainImg.setCollabPost(collabPost);

        // 이벤트 null 이면 저장 x
        if (requestDTO.getEventImgKeyNameList() != null && !requestDTO.getEventImgKeyNameList().isEmpty()) {
            requestDTO.getEventImgKeyNameList().forEach(keyName -> { // event
                CollabPostImg eventImg = CollabPostImg.builder()
                        .keyName(keyName)
                        .imageType(ImageType.EVENT)
                        .build();
                eventImg.setCollabPost(collabPost);
            });
        }


        // 팀 멤버 하드 코딩
        collabPost.setClubMember(clubMemberRepository.findById(1L).orElseThrow(() ->
                new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND)));
        collabPost.setEventType(eventTypeRepository.findById(requestDTO.getEventTypeId()).orElseThrow(() ->
                new EventTypeHandler(ErrorStatus.EVENT_TYPE_NOT_FOUND)));


        return collabPostRepository.save(collabPost);
    }
    @Override
    @Transactional
    public CollabPost modifyCollabPost(Long collabPostId, CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        CollabPost collabPost = collabPostRepository.findByIdWithCollabPostImg(collabPostId);

        EventType eventType = eventTypeRepository.findById(requestDTO.getEventTypeId()).orElseThrow(() ->
                new EventTypeHandler(ErrorStatus.EVENT_TYPE_NOT_FOUND));

        List<CollabPostImg> imagesToDelete = new ArrayList<>();
        List<CollabPostImg> imagesToAdd = new ArrayList<>();
        List<CollabPostImg> collabPostImgListCopy = new ArrayList<>(collabPost.getCollabPostImgList());


        collabPostImgListCopy.forEach(
                collabPostImg -> {
                    switch (collabPostImg.getImageType()) {
                        case BANNER:
                            if (!collabPostImg.getKeyName().equals(requestDTO.getBannerKeyName())) {
                                imagesToDelete.add(collabPostImg);
                                CollabPostImg newBannerImg = CollabPostImg.builder()
                                        .keyName(requestDTO.getBannerKeyName())
                                        .imageType(ImageType.BANNER)
                                        .build();
                                newBannerImg.setCollabPost(collabPost);
                                imagesToAdd.add(newBannerImg);
                            }
                            break;
                        case MAIN:
                            if (!collabPostImg.getKeyName().equals(requestDTO.getMainKeyName())) {
                                imagesToDelete.add(collabPostImg);
                                CollabPostImg newMainImg = CollabPostImg.builder()
                                        .keyName(requestDTO.getMainKeyName())
                                        .imageType(ImageType.MAIN)
                                        .build();
                                newMainImg.setCollabPost(collabPost);
                                imagesToAdd.add(newMainImg);
                            }
                            break;
                        case EVENT:
                            if (!requestDTO.getEventImgKeyNameList().contains(collabPostImg.getKeyName())) {
                                imagesToDelete.add(collabPostImg);
                            }
                            break;
                    }
                }
        );

        if (requestDTO.getEventImgKeyNameList() != null && !requestDTO.getEventImgKeyNameList().isEmpty()) {
            requestDTO.getEventImgKeyNameList().forEach(eventImgKeyName -> {
                boolean exists = collabPost.getCollabPostImgList().stream()
                        .anyMatch(collabPostImg -> collabPostImg.getKeyName().equals(eventImgKeyName)
                                && collabPostImg.getImageType() == ImageType.EVENT);
                if (!exists) {
                    CollabPostImg newEventImg = CollabPostImg.builder()
                            .keyName(eventImgKeyName)
                            .imageType(ImageType.EVENT)
                            .build();
                    newEventImg.setCollabPost(collabPost);
                    imagesToAdd.add(newEventImg);
                }
            });
        } else {
            // 이벤트 이미지 키 리스트가 비어있거나 null인 경우, 기존 이벤트 이미지 삭제
            collabPost.getCollabPostImgList().stream()
                    .filter(collabPostImg -> collabPostImg.getImageType() == ImageType.EVENT)
                    .forEach(imagesToDelete::add);
        }


        collabPostImgRepository.deleteAll(imagesToDelete);
        collabPost.getCollabPostImgList().removeAll(imagesToDelete);

        collabPost.getCollabPostImgList().addAll(imagesToAdd);
        collabPostImgRepository.saveAll(imagesToAdd);


        collabPost.updateCollabPost(requestDTO, eventType);

        // 카테고리 수정
        if (requestDTO.getCategoryIds() != null) {
            List<Category> categoryList =
                    requestDTO.getCategoryIds().stream()
                            .map(categoryId ->{
                                Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                                        new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
                                return category;
                            })
                            .collect(Collectors.toList());

            // 기존 카테고리 별 콜라보 글 삭제 및 목록 비우기
            // (cascade = CascadeType.ALL, orphanRemoval = true) 설정으로 연관된 자식 객체가 부모 객체와의 관계에서 제거될 때 자동으로 삭제 됨
            // 따라서 삭제 작업을 별도로 수행 안 해도 됨
            collabPost.getCollabPostCategoryList().clear();

            categoryList.forEach(category -> {
                CollabPostCategory collabPostCategory = CollabPostCategory.builder()
                        .category(category)
                        .collabPost(collabPost)
                        .build();
                collabPost.getCollabPostCategoryList().add(collabPostCategory); // 새로운 카테고리 별 콜라보 글 추가
            });
        }

        // 컨텍트 방식 수정
        if (requestDTO.getContactMethodDTOList() != null) {
            // 기존 ContactMethod 삭제 및 목록 비우기
            // (cascade = CascadeType.ALL, orphanRemoval = true) 설정으로 연관된 자식 객체가 부모 객체와의 관계에서 제거될 때 자동으로 삭제 됨
            // 따라서 삭제 작업을 별도로 수행 안 해도 됨
            collabPost.getContactMethodList().clear();

            requestDTO.getContactMethodDTOList().forEach(contactMethodDTO -> {
                ContactMethod contactMethod = ContactMethod.builder()
                        .contactType(contactMethodDTO.getContactType())
                        .contactUrl(contactMethodDTO.getContactUrl())
                        .collabPost(collabPost)
                        .build();
                collabPost.getContactMethodList().add(contactMethod); // 새로운 연락처 목록 추가
            });

        }

        return collabPostRepository.save(collabPost);
    }

        @Override
        public void deleteCollabPost (Long collabPostId){

            CollabPost collabPost = collabPostRepository.findById(collabPostId).orElseThrow(() ->
                    new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND));

            collabPostRepository.delete(collabPost);

        }
}
