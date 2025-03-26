package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.ProjectComment;
import com.partnerd.domain.QMember;
import com.partnerd.domain.QProjectComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectCommentRepositoryImpl implements ProjectCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QProjectComment qProjectComment = QProjectComment.projectComment;
    private final QMember qMember = QMember.member;

    // 프로젝트 모집 댓글 전체 조회
    @Override
    public List<ProjectComment> findProjectCommentList(Long projectId) {
        return queryFactory.selectFrom(qProjectComment)
                .leftJoin(qProjectComment.children).fetchJoin()
                .leftJoin(qProjectComment.member, qMember).fetchJoin()
                .where(
                        qProjectComment.project.id.eq(projectId)
                                .and(qProjectComment.parentComment.isNull())
                )
                .orderBy(qProjectComment.createdAt.asc())
                .fetch();
    }
}
