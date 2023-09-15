/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 추천 받은 음악 제목 수정
*/
/*작성자: 신지훈
날짜(수정포함): 2023-09-14
설명: PlaylistRename 반응형 구현
*/
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

const PlaylistRename = () => {
  const [title, setTitle] = useState(""); //플레이 리스트 제목
  const navigate = useNavigate();
  const id = localStorage.getItem("id");

  // 현재 날짜와 시간 구하기
  const todayTime = () => {
    let now = new Date();
    let todayYear = now.getFullYear();
    let todayMonth = now.getMonth() + 1;
    let todayDate = now.getDate();
    let hours = now.getHours();
    let minutes = now.getMinutes();

    return todayYear + "년 " + todayMonth + "월 " + todayDate + "일 " + hours + "시 " + minutes + "분 당신을 위한 음악";
  };

  const SavedRecPlaylist = () => {
    //   const requestData = { id: id, title : title }
    //   axios.get(``, requestData)
    //   .then((res) => {
    //     console.log(res)
    navigate("/RecPlayList");

    //   }).catch((err) => console.log(err))
  };

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full">
      <br />
      <br />
      <br />
      {/* 추천 리스트 제목 */}
      <div className="mt-52 text-center">
        <h1 className="text-xl font-semibold tracking-tight">추천 플레이리스트의 제목을 정해주세요.</h1>
      </div>

      {/* 제목에 기본값으로 현재 날짜와 시간 넣어주기 */}
      <div className="mt-12 flex flex-col md:items-center items-end">
        <input
          type="email"
          className="focus:border-custom-white md:w-1/5 pl-2 w-full border border-soild rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.35] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
          placeholder={todayTime()}
          onChange={(e) => setTitle(e.target.value)}
        />

        {/* 저장과 취소 버튼 */}
        <div className="flex space-x-2 mt-[1rem] md:justify-end">
          {" "}
          {/* mt-[1rem] 을 통해 위의 input과의 간격 조정 */}
          <div className="h-[35px] w-[53px] border border-soild border-#FDFDFD rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
            <button onClick={SavedRecPlaylist}>저장</button>
          </div>
          <div className="h-[35px] w-[53px] border border-soild border-#FDFDFD rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
            <Link to="/RecPlayList">
              <button>취소</button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PlaylistRename;
