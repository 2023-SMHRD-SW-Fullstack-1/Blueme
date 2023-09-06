import React from "react";
import { Link } from "react-router-dom";
import UserDummy from '../../dummy/UserDummy.json'
const LoadDataCompl = () => {
  const now = new Date();

  // 오늘 날짜&시간
  const date = ` ${now.getMonth()+1}월 ${now.getDate()}일`;
  const time = `${now.getHours()}시 ${now.getMinutes()}분`;
  
  return (
    <div className="bg-custom-blue text-custom-white text-center flex flex-col min-h-screen justify-center items-center">
      <div>
        <p className="text-l m-10">
          스마트 워치에서 데이터를 가져온 결과,
          <br />
          {date} {time}
          <br />
          “{UserDummy[0].nickname}” 님의 현재 상태입니다.
        </p>
        <p className="text-xs text-custom-lightpurple m-5">Blueme가 당신에게 맞는 음악을 추천해드릴게요.</p>
      </div>
      <div>
        <p className="m-5">심박수 120bpm</p>
        <p className="m-5">스트레스 지수 18</p>
        <p className="m-5">속도 80m/s</p>
      </div>
      <Link to="/LoadGpt" className="border rounded-xl text-l p-3 m-3">
        <button>추천받기</button>
      </Link>
    </div>
  );
};

export default LoadDataCompl;
