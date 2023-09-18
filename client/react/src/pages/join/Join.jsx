import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import kakao from "../../assets/img/kakao.png";
import google from "../../assets/img/google.png";
import { joinFailure, joinRequest, joinSuccess } from "../../store/member/memberAction";
import '../../App.css'

/*
작성자: 이유영
날짜(수정포함): 2023-09-11
설명: 회원가입 기능 구현 및 리덕스 추가
*/
/*
작성자: 신지훈
날짜: 2023-09-14
설명: 회원가입 
*/
const Join = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const user = useSelector(state => state.memberReducer.user)
  const navigate = useNavigate();
  const dispatch = useDispatch()

  //3초 로딩 함수
  const timeout = () => {
    setTimeout(() => {
      document.getElementById('toast-warning').classList.remove("reveal")
      navigate('/Join')
    }, 2000);// 원하는 시간 ms단위로 적어주기
  };

  // 회원가입 버튼 클릭 시 실행되는 함수
  const handleJoin = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }
    dispatch(joinRequest())

    const requestData = { email : email, password : password, nickname : nickname}

     await axios
     .post("http://172.30.1.45:8104/user/signup", requestData)
     .then((res) => {
        console.log(user);
        localStorage.setItem('id' , res.data)
        navigate('/selectGenre')
     })
     .catch((err) => {
      console.log(err)
      document.getElementById('toast-warning').classList.add("reveal")
      timeout()
      dispatch(joinFailure(err.massage))
     })
  
    }



  return (
    <div className=" min-h-screen bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 tracking-tight flex flex-col px-4 sm:px-8 md:px-16">
      <br />
      <div className="flex items-center justify-center h-screen w-full p-4">
        <div className="w-full md:w-1/2 lg:w-1/4">
          <div className="text-custom-white text-left mt-5 text-xl font-semibold w-full">Blueme 일반 계정 회원가입</div>

          <input
            type="email"
            onChange={(e) => setEmail(e.target.value)}
            className="bg-gradient-to-t from-gray-900 tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-8 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="이메일을 입력해주세요."
          />

          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
            className="bg-gradient-to-t from-gray-900 tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
            placeholder="비밀번호를 입력해주세요."
          />

          <input
            type="password"
            onChange={(e) => setConfirmPassword(e.target.value)}
            className="bg-gradient-to-t from-gray-900 tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
            placeholder="비밀번호를 다시 입력해주세요."
          />

          <input
            type="text"
            onChange={(e) => setNickname(e.target.value)}
            className="bg-gradient-to-t from-gray-900 tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="닉네임을 입력해주세요."
          />

          <button
            onClick={handleJoin}
            className="
             mt-10
             h-11
             px-3 relative 
             bg-[#221a38]  
             rounded-lg border border-soild border-[#fdfdfd]
             text-custom-white
             tracking-tighter
             font-bold
             md:w-full w-full"
          >
            회원가입
          </button>
          <hr className="mt-12 " style={{ borderTop: "1px solid gray", width: "100%" }} />
          <div className="text-custom-white mt-12 text-left w-full text-xl pont-semibold">SNS계정 회원가입</div>
          <button
            className="
     flex items-center justify-center pl-2 w-full mt-8 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
          >
            <img src={kakao} alt="" className="w-[6%] h-auto mr-1" />
            카카오로 로그인
          </button>
          <button
            className="
  flex items-center justify-center pl-2 w-full mt-5 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
          >
            <img src={google} alt="Google logo" className="mr-1 w-[5%] h-auto" />
            구글로 로그인
          </button>
          <div className="pb-10"></div>
        </div>
      </div>
      {/* 토스트 창 띄우기 */}
      <div className="flex justify-center items-center">
          <div id="toast-warning" className="flex items-center border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert">
            <div className="ml-3 font-normal text-center">회원가입에 실패하셨습니다.</div>
          </div>
        </div>
    </div>
  );
};

export default Join;
