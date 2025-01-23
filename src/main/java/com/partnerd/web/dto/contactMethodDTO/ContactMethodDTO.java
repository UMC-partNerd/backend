package com.partnerd.web.dto.contactMethodDTO;


import com.partnerd.domain.ContactMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactMethodDTO {
    private String contactType;
    private String contactUrl;

    // 기본 생성자 필요
    public ContactMethodDTO() {}


    public ContactMethodDTO(ContactMethod contactMethod) {
        this.contactType = contactMethod.getContactType();
        this.contactUrl = contactMethod.getContactUrl();
    }

    public static ContactMethodDTO toContactMethodDTO(ContactMethod contactMethod) {
        return new ContactMethodDTO(contactMethod);
    }
}
