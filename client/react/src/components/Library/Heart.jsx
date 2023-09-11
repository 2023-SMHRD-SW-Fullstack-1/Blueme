import { useEffect, useState, useRef } from "react";
import axios from "axios";
import likeEmpty from '../../assets/img/likeEmpty.png'
import likeFull from "../../assets/img/likeFull.png";


const Heart = ({item}) => {
// 좋아요 버튼 관련
const [isSaved, setIsSaved] = useState(-1); // 초기 좋아요 상태 불러오기

// 임의의 사용자id
const userId = 1;

 // 좋아요 상태 확인
 useEffect(() => {
   const fetchLikeStatus = async () => {
     try {
       const response = await axios.post(
         "/likemusics/issave",
         { userId: userId.toString(), musicId: item.toString() }
       );
       setIsSaved(parseInt(response.data));
     } catch (error) {
       console.error("좋아요 불러오기 실패", error);
     }
   };
   fetchLikeStatus();
 }, [userId, item]);


  return (
    <div>
      <button>
    <img
      className="w-[30px] h-auto"
      src={isSaved ? likeFull : likeEmpty}
      alt="like-button"
    />
  </button>
  </div>
  )
}

export default Heart