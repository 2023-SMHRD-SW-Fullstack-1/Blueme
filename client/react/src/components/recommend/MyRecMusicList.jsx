/*
작성자: 이유영
날짜(수정포함): 2023-09-20
설명: 나의 추천 플레이리스트 컴포넌트
*/

import React, { useState } from "react";
import Play from '../../assets/img/play.png'
import Slider from "react-slick";
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';


const MyRecMusicList = ({ item, i }) => {
  const [isMouseOver, setIsMouseOver] = useState(false)


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
    autoplaySpeed: 3000, // 각 슬라이드가 보여지는 시간 (밀리초 단위)
  }

  return (
    <div>
       <div className="flex flex-col justify-center items-center mt-8 lg:pl-[15px] ">
          <div className="relative w-[180px] h-auto " onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
            <Slider {...setting}>
              {item.recMusiclistDetails.map((detail, i) => (
                <div key={i}>
                  <img src={"data:image/;base64,"+detail.img} alt="album cover"/>
                </div>
              ))}
            
            </Slider>
            {isMouseOver && 
              <>
                <img src={Play} alt="noimg" className="absolute top-0 right-0 w-[30px]" />
                <p className="absolute bottom-0 left-0 w-full h-[105px] overflow-hidden text-white bg-black bg-opacity-50 p-2">{item.reason}</p>
              </>
            }
          </div>
          <p className="tracking-tight text-normal text-center mt-3 w-[180px] ">
            {item.title}
          </p>
        </div>
    </div>
  );
};

export default MyRecMusicList;
