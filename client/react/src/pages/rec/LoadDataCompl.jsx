/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 스마트 워치 데이터 가져오기
*/
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from 'axios';


const LoadDataCompl = () => {
  const [heartRate, setHeartRate] = useState('')
  const [step, setStep] = useState('')
  const [speed, setSpeed] = useState('')
  let id = localStorage.getItem('id')

  // 오늘 날짜&시간
  const now = new Date();
  const date = ` ${now.getMonth()+1}월 ${now.getDate()}일`;
  const time = `${now.getHours()}시 ${now.getMinutes()}분`;

  useEffect(()=> {
    axios.get(`http://172.30.1.27:8104/healthinfo/get/${id}`)
    .then((res) => {
      setHeartRate(res.data.avgHeartRate)
      setStep(res.data.stepsPerMinute) 
      setSpeed(res.data.avgSpeed)
    console.log(res)})
    .catch((err) => {console.log(err)})
  }, [])  
  
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full ">
      <br/><br/><br/>
      
      {/* 현재 데이터 가져오기 */}
      <div className="text-right text-base text-custom-lightgray tracking-tighter mt-[70px]">
        <Link to='/RecAppDes'>
          <button className="text-sm w-[140px] p-1 h-[40px]">현재 데이터 가져오기</button></Link>
      </div>
      <div>
        <p className="font-bold leading-normal mt-12 text-center text-[22px]">
          스마트 워치에서 데이터를 가져온 결과,
          <br />
          {date} {time}
          <br />
          {localStorage.getItem('nickname')} 님의 현재 상태입니다.
        </p>
        <p className="font-semibold text-[17px] tracking-tighter text-center text-custom-lightpurple mt-12">Blueme가 당신에게 맞는 음악을 추천해드릴게요.</p>
      </div>

      {/* 심박수, 스트레스 지수, 속도 결과 */}
      <div className="m-12 font-semibold tracking-tighter text-center text-[20px]">
        <p className="m-5">심박수 {heartRate}bpm</p>
        <p className="m-5">걸음 수 {step}</p>
        <p className="m-5">속도 {speed}m/s</p>
      </div>
      <div className="flex justify-center item-center">
        <Link to="/LoadGpt">
          <button className="border tracking-tighter border-soild border-#FDFDFD rounded-xl p-3 leading-[1.35]">추천받기</button>
        </Link>
      </div>
    </div>
  );
};

export default LoadDataCompl;
