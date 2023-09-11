/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 라이브러리 페이지 내 좋아요 누른 곡 리스트 
*/

import { useEffect, useState } from "react";
import SingleMusic from './SingleMusic.jsx';
import dummy from '../../dummy/MusicDummy.json';
import axios from 'axios';


const LikedList = () => {
// 임의의 사용자 아이디
let userId = 1;

const [likedMusics, setLikedMusics] = useState([]);

useEffect(() => {
  const fetchLikedList = async () => {
    try {
      const response = await axios.get(`/likemusics/${userId}`);
      let musicArray = response.data.map((item) => ({
        id: item.musicId,
        title: item.title,
        artist: item.artist,
        img: item.img,
      }));
      setLikedMusics(musicArray);
    } catch (error) {
      console.error("좋아요 목록 불러오기 실패", error);
    }
  };
  fetchLikedList();
}, [userId]);
    return (
        <div>
            {likedMusics.slice(0, 5).map((item) => (
                <SingleMusic key={item.id} item={item} />
            ))}
        </div>
    );
};

export default LikedList;
