/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: MusicIds 수정
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-18
설명: 반응형 구현
*/

import { useEffect, useState } from "react";
import SingleMusic from "./SingleMusic.jsx";
import axios from "axios";
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";

const LikedList = () => {
  // 임의의 사용자 아이디
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  const dispatch = useDispatch();

  const [likedMusics, setLikedMusics] = useState([]);

  // 현재 화면 크기가 데스크탑 크기인지 저장하는 상태
  const [isDesktop, setIsDesktop] = useState(window.innerWidth >= 1024);

  // musicIds 설정
  const [playingIds, setPlayingIds] = useState([]);

  useEffect(() => {
    window.addEventListener("resize", handleResize);

    // Clean up the event listener on component unmount
    return () => {
      window.removeEventListener("resize", handleResize);
    };

    function handleResize() {
      // 이전과 다른 브레이크포인트에 도달했는지 확인하고 상태 업데이트
      const desktopSizeReached = window.innerWidth >= 1024;
      if (desktopSizeReached !== isDesktop) setIsDesktop(desktopSizeReached);
    }
  }, [isDesktop]);

  useEffect(() => {
    async function fetchLikedList() {
      try {
        let response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/likemusics/${userId}`);
        let ids;

        if (isDesktop) {
          ids = response.data.slice(0, 10).map((music) => music.musicId);
          setLikedMusics(response.data.slice(0, 10));
        } else {
          ids = response.data.slice(0, 6).map((music) => music.musicId);
          setLikedMusics(response.data.slice(0, 6));
        }

        console.log("ids", ids);
        setPlayingIds(ids);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    }

    fetchLikedList();
  }, [isDesktop]);

  // musicIds 설정
  const handleListClick = () => {
    dispatch(setMusicIds(playingIds));
  };

  return (
    <div className="hide-scrollbar overflow-scroll" onClick={handleListClick}>
      {likedMusics.map((song) => (
        <SingleMusic key={song.musicId} item={song} />
      ))}
    </div>
  );
};

export default LikedList;
