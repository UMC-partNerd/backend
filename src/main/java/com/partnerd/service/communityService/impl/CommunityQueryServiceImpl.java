package com.partnerd.service.communityService.impl;

import com.partnerd.converter.communityConverter.CommunityConverter;
import com.partnerd.domain.Community;
import com.partnerd.repository.communityRepository.CommunityRepository;
import com.partnerd.service.communityService.CommunityQueryService;
import com.partnerd.web.dto.CommunityDTO.CommunityResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityQueryServiceImpl implements CommunityQueryService {

    private final CommunityRepository communityRepository;
    @Override
    public CommunityResponseDTO.CommunityPreviewListDTO getCommunityList(Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);

        // hasNext 계산 (size + 1개를 가져왔을 경우)
        List<Community> communityList = communityRepository.findByCursorPagingWithMember(cursor, pageable);
        boolean hasNext = communityList.size() > size;
        Long nextCursor = hasNext ? communityList.get(size).getId() : null;

        // 결과 리스트 조정 (size + 1개를 가져왔으므로 마지막 요소 제거)
        if (hasNext) {
            communityList.remove(size);
        }

        return  CommunityConverter.communityPreviewListDTO(communityList, hasNext, nextCursor);
    }

    @Override
    public List<CommunityResponseDTO.CommunityTop10PreviewDTO> getCommunityTop10List() {

        List<Community> top10Community = communityRepository.findTop10ByLikes();
        List<CommunityResponseDTO.CommunityTop10PreviewDTO> CommunityTop10PreviewListDTO = top10Community.stream()
                .map(CommunityConverter::communityTop10PreviewDTO).collect(Collectors.toList());

        return CommunityTop10PreviewListDTO;
    }
}
