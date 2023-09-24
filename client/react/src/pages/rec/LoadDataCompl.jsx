/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 스마트 워치 데이터 가져오기(+member리덕스)
*/

/*작성자: 신지훈
날짜(수정포함): 2023-09-22
설명: 모바일 크기 조정
*/
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";

const LoadDataCompl = () => {
  const [heartRate, setHeartRate] = useState("");
  const [step, setStep] = useState("");
  const [speed, setSpeed] = useState("");
  const [date, setDate] = useState("");
  const user = useSelector((state) => state.memberReducer.user); //member리덕스 가져오기
  const nickname = user.nickname; //member 리덕스에서 nickname가져오기
  const id = user.id; //id가져오기
  // console.log('header',user);
  const navigate = useNavigate();

  // // 오늘 날짜&시간
  // const now = new Date();
  // const date = ` ${now.getMonth()+1}월 ${now.getDate()}일`;
  // const time = `${now.getHours()}시 ${now.getMinutes()}분`;

  useEffect(() => {
    axios
      .get(`http://172.30.1.27:8104/healthinfo/get/${id}`) //사용자 건강데이터 불러오기
      .then((res) => {
        setHeartRate(res.data.avgHeartRate); //심박수
        setStep(res.data.stepsPerMinute); //걸음수
        setSpeed(res.data.avgSpeed); //속도
        setDate(res.data.createdAt); //건강데이터 생성 시간
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  //date에 넘어오는 2023-09-19T12:19:43.470645 값 잘라서 넣기
  const year = date.substring(0, 4); //년
  const month = date.substring(5, 7); //월
  const day = date.substring(8, 10); //일
  const hour = date.substring(11, 13); //시
  const minute = date.substring(14, 16); //분
  const second = date.substring(17, 19); //초

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full hide-scrollbar overflow-auto ">
      <br />
      <br />
      <br />
      <div>
        <p className=" leading-normal xs:mt-[30px] lg:mt-[160px] text-center text-[20px]">
          스마트 워치에서 데이터를 가져온 결과,
          <br />
          {/* 사용자가 건강 데이터 보낸 시간 */}
          {year}년 {month}월 {day}일 {hour}시 {minute}분 {second}초
          <br />ㅞ{nickname} 님의 현재 상태입니다.
        </p>

        {/* 현재 데이터 가져오기 */}
        <div className="text-center text-base text-custom-lightgray tracking-tight mt-[30px]">
          <Link to="/RecAppDes">
            <button className="text-sm w-[180px] p-1 bg-gradient-to-t from-gray-600 rounded-xl">
              현재 건강 데이터 가져오기
            </button>
          </Link>
        </div>

        <p className="text-[17px] tracking-tight text-center text-custom-lightpurple mt-12">
          Blueme가 당신에게 맞는 음악을 추천해드릴게요.
        </p>
      </div>

      {/* 심박수, 스트레스 지수, 속도 결과 */}
      <div className="m-12  tracking-tighter text-center text-[20px]">
        <p className="m-5">심박수 {heartRate}bpm</p>
        <p className="m-5">걸음 수 {step}</p>
        <p className="m-5">속도 {speed}km/h</p>
      </div>
      <div className="flex justify-center item-center">
        <Link to="/LoadGpt">
          <button className="border tracking-tight border-soild border-#FDFDFD rounded-xl p-3 leading-[1.35]">
            추천받기
          </button>
        </Link>
      </div>
    </div>
  );
};

export default LoadDataCompl;
