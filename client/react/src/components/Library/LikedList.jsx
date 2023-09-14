/*
작성자: 이지희
날짜(수정포함): 2023-09-12
설명: 라이브러리 페이지 내 좋아요 누른 곡 5개 리스트 
*/

import { useEffect, useState } from "react";
import SingleMusic from './SingleMusic.jsx';
import axios from 'axios';
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/setMusicIds";

const LikedList = () => {
// 임의의 사용자 아이디
let userId = 1;

const dispatch = useDispatch();
  const musicIds = useSelector(state => state);

  const [likedMusics, setLikedMusics] = useState([]);

useEffect(() => {
  const fetchLikedList = async () => {
    try { 
      const response = await axios.get(`/likemusics/${userId}`);
      setLikedMusics(response.data);
      let ids = response.data.slice(0, 5).map(music => music.musicId);
      console.log('ids', ids);
      dispatch(setMusicIds(ids));
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  fetchLikedList();
}, []);

    return (
        <div>
            {likedMusics.slice(0, 5).map((song) => (
                <SingleMusic key={song.musicId} item={song} />
            ))}
        </div>
    );
};

export default LikedList;
