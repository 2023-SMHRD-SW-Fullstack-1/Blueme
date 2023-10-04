/*
작성자: 이유영
날짜(수정포함): 2023-09-20
설명:추천 플레이리스트 최신 10개 컴포넌트
*/

import React, { useState } from "react";
import Play from '../../assets/img/play.png'



const OtherRecMusicList = ({ item }) => {

  const [isMouseOver, setIsMouseOver] = useState(false)//마우스오버 여부 판단

  const handleMouseOver = () => {
    setIsMouseOver(true)
  }

  const handleMouseOut = () => {
    setIsMouseOver(false)
  }

  const setting = {
    arrows: false,
    infinite: true,
    autoplay: true, // 자동 재생 활성화
    autoplaySpeed: 5000, // 각 슬라이드가 보여지는 시간 (밀리초 단위)
  }


  return (
    <div>
       <div className="flex flex-col justify-center items-center mt-8 lg:pl-[15px] hover:shadow-inner">
          <div className="relative w-[180px] h-auto rounded-lg" onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
            <img src={"data:image/;base64,"+ item.img} alt="album cover" className="rounded-lg"/>
            {isMouseOver && 
              <>
                <img src={Play} alt="noimg" className="absolute top-1 right-1 w-[25px]" />
                {/* <p className="absolute bottom-0 left-0 w-full h-[105px] overflow-hidden  text-white bg-black bg-opacity-50 p-2">{item.recMusiclistReason}</p> */}
              </>
            }
          </div>
          <p className="tracking-tight text-sm text-center mt-3 w-[180px] font-semibold">
            {item.recMusiclistTitle}
          </p>
        </div>
    </div>

  );
};

export default OtherRecMusicList;