import React from "react";
import { Link } from "react-router-dom";
import UserDummy from '../../dummy/UserDummy.json'
const LoadDataCompl = () => {
  const now = new Date();

  // 오늘 날짜&시간
  const date = ` ${now.getMonth()+1}월 ${now.getDate()}일`;
  const time = `${now.getHours()}시 ${now.getMinutes()}분`;
  
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full ">
      <br/><br/><br/>
      {/* 최신 데이터 불러오기 */}
      <div className="text-right text-base text-custom-lightgray tracking-tighter mt-3">
        <Link to='/RecAppDes'><p>최신 데이터 불러오기</p></Link>
      </div>

      <div className="">
        <p className="font-bold leading-normal mt-32 text-center text-xl/[20px]">
          스마트 워치에서 데이터를 가져온 결과,
          <br />
          {date} {time}
          <br />
          {UserDummy[0].nickname} 님의 현재 상태입니다.
        </p>
        <p className="font-semibold text-normal tracking-tighter text-center text-custom-lightpurple mt-12">Blueme가 당신에게 맞는 음악을 추천해드릴게요.</p>
      </div>
      <div className="m-14 font-semibold tracking-tighter text-center text-xl/[20px]">
        <p className="m-5">심박수 120bpm</p>
        <p className="m-5">스트레스 지수 18</p>
        <p className="m-5">속도 80m/s</p>
      </div>
      <div className="flex justify-center item-center">
        <Link to="/LoadGpt">
          <button className="border-2 text-base tracking-tighter mt-6 border-soild border-#FDFDFD rounded-xl p-3 leading-[1.35]">추천받기</button>
        </Link>
      </div>
    </div>
  );
};

export default LoadDataCompl;
