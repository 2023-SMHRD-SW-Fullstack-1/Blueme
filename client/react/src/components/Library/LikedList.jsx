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
  const user = useSelector(state => state.memberReducer.user)
  const userId = user.id

  const dispatch = useDispatch();
  const musicIds = useSelector((state) => state.musicReducer.musicIds);

  const [likedMusics, setLikedMusics] = useState([]);
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  const handleResize = () => {
    setWindowWidth(window.innerWidth);
  };

  useEffect(() => {
    window.addEventListener("resize", handleResize);

    // Clean up the event listener on component unmount
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  useEffect(() => {
    const fetchLikedList = async () => {
      try {
        const response = await axios.get(`/likemusics/${userId}`);
        setLikedMusics(response.data);
        let ids;
        if (windowWidth >= 1024) {
          ids = response.data.slice(0, 10).map((music) => music.musicId);
        } else {
          ids = response.data.slice(0, 5).map((music) => music.musicId);
        }
        console.log("ids", ids);
        dispatch(setMusicIds(ids));
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchLikedList();
  }, [windowWidth]);

  return (
    <div>
      {likedMusics.slice(0, windowWidth >= 1024 ? 10 : 5).map((song) => (
        <SingleMusic key={song.musicId} item={song} />
      ))}
    </div>
  );
};

export default LikedList;
