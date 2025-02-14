package com.partnerd.converter.collabAskConverter;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.web.dto.collabDTO.response.CollabAskResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CollabAskConverter {


    public static CollabAskResponseDTO.CollabAskPreviewDTO collabAskPreviewDTO (CollabAsk collabAsk) {

        return CollabAskResponseDTO.CollabAskPreviewDTO.builder()
                .collabAskId(collabAsk.getId())
                .collabPostId(collabAsk.getCollabPost().getId())
                .collabPostTitle(collabAsk.getCollabPost().getTitle())
                .clubName(collabAsk.getReceiver().getClub().getName())
                .build();
    }



    public static CollabAskResponseDTO.CollabAskPreviewListDTO collabAskPreviewListDTO (Page<CollabAsk> collabAskPage) {

        List<CollabAskResponseDTO.CollabAskPreviewDTO> collabAskPreviewDTOList = collabAskPage.stream()
                .map(CollabAskConverter::collabAskPreviewDTO).collect(Collectors.toList());

        return CollabAskResponseDTO.CollabAskPreviewListDTO.builder()
                .collabAskPreviewDTOLList(collabAskPreviewDTOList)
                .totalPage(collabAskPage.getTotalPages())
                .totalElements(collabAskPage.getTotalElements())
                .listSize(collabAskPage.getSize())
                .isFirst(collabAskPage.isFirst())
                .isLast(collabAskPage.isLast())
                .build();

    }




    public static CollabAskResponseDTO.addCollabAskResponseDTO toAddCollabAskResponseDTO (CollabAsk collabAsk, ChatRoom chatRoom) {

        return CollabAskResponseDTO.addCollabAskResponseDTO.builder()
                .collabAskId(collabAsk.getId())
                .receiverId(collabAsk.getReceiver().getId())
                .senderId(collabAsk.getSender().getId())
                .colloabPostTitle(collabAsk.getCollabPost().getTitle())
                .chatRoomId(chatRoom.getId())
                .build();


    }
}
