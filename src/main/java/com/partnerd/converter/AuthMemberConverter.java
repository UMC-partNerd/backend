package com.partnerd.converter;

import com.partnerd.domain.Member;
import com.partnerd.web.dto.registerDTO.RegisterRequestDTO;
import com.partnerd.web.dto.registerDTO.RegisterResponseDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AuthMemberConverter {

    public static Member toMemberEntity(RegisterRequestDTO request, Member member) {
        member.setName(request.getName());
        member.setNickname(request.getNickname());

        try {
            // 생년월일 변환 (YYYYMMDD -> Date)
            String birthDateString = request.getBirthDate();
            if (birthDateString == null || birthDateString.isEmpty()) {
                throw new IllegalArgumentException("Birth date is null or empty");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            member.setBirth(dateFormat.parse(birthDateString));

        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid birth date format: " + request.getBirthDate(), e);
        }

        return member;
    }

    public static RegisterResponseDTO toRegisterResponseDTO(Member member) {
        return RegisterResponseDTO.builder()
                .userId(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .birthDate(member.getBirth())
                .build();
    }
}
