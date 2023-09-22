/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: MusicIds 수정
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-20
설명: 디자인 수정
*/

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";

// import Component
import SingleMusic from "../../components/Library/SingleMusic";

const LikedPlaylist = () => {
  const navigate = useNavigate();

  // 사용자 user_id
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  const [ids, setIds] = useState([]);
  const [likedMusics, setLikedMusics] = useState([]);

  const dispatch = useDispatch();

  useEffect(() => {
    const fetchLikedList = async () => {
      try {
        const response = await axios.get(`/likemusics/${userId}`);
        setLikedMusics(response.data);
        setIds(response.data.map((music) => music.musicId));
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchLikedList();
  }, []);

  const handleListClick = () => {
    dispatch(setMusicIds(ids));
  };

  // 랜덤재생
  const randomize = () => {
    let array = [...ids];
    for (let i = array.length - 1; i > 0; i--) {
      let j = Math.floor(Math.random() * (i + 1));
      [array[i], array[j]] = [array[j], array[i]];
    }
    setIds(array);
    dispatch(setMusicIds(array));
    navigate(`/MusicPlayer/${array[0]}`);
  };

  return (
    <div
      className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700  text-custom-white h-full pt-20"
    >
      {/* 플레이리스트 */}
      <div className="flex flex-col items-center justify-center">
        <p className="text-3xl pt-5">내가 좋아요 누른 곡</p>
      </div>
      <div className="flex justify-center ml-4 mr-4 p-8">
        <button
          className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple tracking-tight w-[160px] h-10 text-[18px]"
          onClick={randomize}
        >
          랜덤 재생
        </button>
      </div>
      {/* 음악 데이터 */}
      <div className="h-[70%] ml-3 mr-3 overflow-y-scroll hide-scrollbar" onClick={handleListClick}>
        {likedMusics.map((song) => (
          <SingleMusic key={song.id} item={song} />
        ))}
      </div>
    </div>
  );
};

export default LikedPlaylist;
