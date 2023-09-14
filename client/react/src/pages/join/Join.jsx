/*
작성자: 이유영
날짜(수정포함): 2023-09-11
설명: 회원가입 구현
*/
import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
// import { useDispatch } from "react-redux";
import kakao from "../../assets/img/kakao.png";
import google from "../../assets/img/google.png";
// import { join } from "../../store/user/user_action";

/*
작성자: 이유영
날짜(수정포함): 2023-09-11
설명: 회원가입 구현
*/
/*
작성자: 신지훈
날짜: 2023-09-04
설명: 회원가입 화면
*/
const Join = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");

  const navigate = useNavigate();
  // const dispatch = useDispatch()

  // 회원가입 버튼 클릭 시 실행되는 함수
  const handleJoin = async (e) => {
    e.preventDefault();
    // dispatch(join({email, password, nickname}))

    // console.log("join", email,password,nickname);

    // navigate('/JoinComplete')

    if (password !== confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }
    try {
      const response = await axios.post("http://172.30.1.45:8104/user/signup", {
        email,
        password,
        nickname,
      });

      console.log(response);
      localStorage.setItem('id', response.data)

      navigate("/SelectGenre");
    } catch (error) {
      if (error.response && error.response.status === 409) {
        console.error("이미 존재하는 계정:", error.response.data);
        alert("이미 존재하는 이메일 주소입니다.");
      } else {
        console.error(error);
        alert("회원가입에 실패했습니다.");
      }
    }
  };
  return (
    <div className=" min-h-screen bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 tracking-tight flex flex-col px-4 sm:px-8 md:px-16">
      <br/>
      <div className="text-custom-white w-full text-left mt-[100px] text-xl font-semibold sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4">
        Blueme 일반 계정 회원가입
      </div>

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
             px-3 h-10 relative 
             bg-[#221a38]  
             rounded-lg border border-soild border-[#fdfdfd]
             text-custom-white
             tracking-tighter
             font-bold"
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
  );
};

export default Join;
