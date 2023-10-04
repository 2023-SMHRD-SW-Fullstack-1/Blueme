/*
작성자: 이지희
날짜(수정포함): 2023-09-25
설명: MusicIds 수정, 랜덤재생구현&디자인수정(0925)
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
import { setMusicIds, setCurrentSongId ,setPlayingStatus } from "../../store/music/musicActions.js";

// import Component
import SingleMusic from "../../components/Library/SingleMusic";

const LikedPlaylist = () => {
  const navigate = useNavigate();
  const currentSongId = useSelector((state) => state.musicReducer.currentSongId)
  // 사용자 user_id
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  const [ids, setIds] = useState([]);
  const [likedMusics, setLikedMusics] = useState([]);

  const dispatch = useDispatch();

  useEffect(() => {
    const fetchLikedList = async () => {
      try {
        
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/likemusics/${userId}`);
        setLikedMusics(response.data);
        setIds(response.data.map((music) => music.musicId));
      } catch (error) {
        console.error(`Error: ${error}`);
        setLikedMusics(null)
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
  dispatch(setPlayingStatus(true))
  
  // 첫 번째 재생곡도 무작위로 선택되도록 수정
  dispatch(setCurrentSongId(array[0]))
  
  navigate(`/MusicPlayer/${currentSongId}`);
};

  return (
    <div
      className="overflow-auto bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 hide-scrollbar text-custom-white h-full pt-24 mb-[140px]"
    >
      {/* 플레이리스트 */}
      <div className="flex flex-col items-center justify-center">
        <p className="text-xl font-semibold">{user.nickname}님이 좋아요 누른 음악</p>
      </div>
      <div className="flex justify-center p-6">
        <button
          className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple tracking-tight w-[140px] h-10 text-[18px]"
          onClick={randomize}
        >
          랜덤 재생
        </button>
      </div>
      {/* 음악 데이터 */}
      <div className="h-[70%] ml-3 mr-3" onClick={handleListClick}>
        {likedMusics === null ? 
        <p className="text-left ml-2 mt-2 text-custom-lightgray text-[15px]">좋아요 누른 음악이 없습니다.</p> 
         : likedMusics.map((song) => (
          <SingleMusic key={song.musicId} item={song} />
        ))}
        
      </div>
    </div>
  );
};

export default LikedPlaylist;
