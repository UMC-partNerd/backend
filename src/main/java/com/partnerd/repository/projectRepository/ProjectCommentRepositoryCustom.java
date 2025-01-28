package com.partnerd.repository.projectRepository;

import com.partnerd.domain.Member;
import com.partnerd.domain.ProjectComment;

import java.util.List;
import java.util.Optional;

public interface ProjectCommentRepositoryCustom {
    // 프로젝트 모집 댓글 전체 조회
    List<ProjectComment> findProjectCommentList(Long projectId);
}
