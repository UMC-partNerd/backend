package com.partnerd.service.homeService.impl;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.repository.projectRepository.ProjectRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.service.homeService.HomeService;
import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import com.partnerd.web.dto.homeDTO.response.HomePromotionProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final CollabPostRepository collabPostRepository;
    private final ClubRepository clubRepository;
    private final ProjectRepository projectRepository;
    private final PromotionProjectRepository promotionProjectRepository;


    @Override
    public List<HomeClubDTO> getPopularClubs(){
        return clubRepository.findTop4ClubByOrderByViewsDesc().stream()
                .map(club -> new HomeClubDTO(
                        club.getProfile(),
                        club.getName(),
                        club.getIntro()))
                .collect(Collectors.toList());

    }

    @Override
    public List<HomeCollabPostDTO> getRecentCollabPosts(){

        return collabPostRepository.findTop4ByOrderByCreatedAtDesc().stream()
                .map(post -> new HomeCollabPostDTO(
                        post.getTitle(),
                        post.getIntro(),
                        post.getClubMember().getClub().getName() //ClubMember를 통해 Club 명 가져오기
                ))
                .collect(Collectors.toList());

    }

    @Override
    public List<HomeProjectDTO> getRecentProjects(){

        return projectRepository.findTop6ByOrderByCreatedAtDesc().stream()
                .map(project -> new HomeProjectDTO(
                        project.getProfile_img_url(),
                        project.getTitle(),
                        project.getIntro()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<HomePromotionProjectDTO> getPopularPromotionProjects(){

        return promotionProjectRepository.findTop6ByOrderByViewsDesc().stream()
                .map(promotionProject -> new HomePromotionProjectDTO(
                        promotionProject.getProfile_img_url(),
                        promotionProject.getTitle(),
                        promotionProject.getIntro()
                ))
                .collect(Collectors.toList());
    }

}
