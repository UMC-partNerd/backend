package com.partnerd.repository.chatRoomRepository.chatRoomMember;

import com.partnerd.mongoRepository.domain.mapping.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>, ChatRoomMemberRepositoryCustom {


}
