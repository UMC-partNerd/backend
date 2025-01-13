package com.partnerd.web.dto.collabDTO.request;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class CollabPostRequestDTO {

    @Getter
    @Setter
    public static class RequestCollabPostDTO {
        private String title;
        private String intro;
        private Date openDate;
        private Date closeDate;
        private Date startDate;
        private Date endDate;
        private String collabTarget;
        private Long eventTypeId;
        private int eventMode;
        private String description;
        private List<ContactMethodDTO> contactMethod;
        private List<Integer> categoryIds;
    }



}
