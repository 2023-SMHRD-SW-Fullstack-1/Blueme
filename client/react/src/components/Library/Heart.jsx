/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 좋아요 표시 컴포넌트 
*/

import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

import axios from "axios";
import likeEmpty from "../../assets/img/likeEmpty.png";
import likeFull from "../../assets/img/likeFull.png";

const Heart = ({ item }) => {
  const [isSaved, setIsSaved] = useState(-1); // 초기 좋아요 상태 불러오기
  const [isLiked, setIsLiked] = useState(isSaved > 0 ? true : false);

  // userId
  const user = useSelector(state => state.memberReducer.user)
  const userId = user.id

  // 좋아요 상태 확인
  useEffect(() => {
    const fetchLikeStatus = async () => {
      try {
        const response = await axios.post("/likemusics/issave", {
          userId: userId.toString(),
          musicId: item.toString(),
        });
        setIsSaved(parseInt(response.data));
        setIsLiked(parseInt(response.data) > 0);
      } catch (error) {
        console.error("좋아요 불러오기 실패", error);
      }
    };
    fetchLikeStatus();
  }, [userId, item]);

  // 좋아요버튼 누르기
  const toggleLike = async () => {
    try {
      await axios.put("/likemusics/toggleLike", {
        userId: userId,
        musicId: item,
      });
      setIsLiked(!isLiked);
    } catch (error) {
      console.error("등록 실패", error);
    }
  };

  return (
    <div>
      <button onClick={toggleLike}>
        <img
          className="w-[30px] h-auto"
          src={isLiked ? likeFull : likeEmpty}
          alt="{likeEmpty}"
        />
      </button>
    </div>
  );
};

export default Heart;
