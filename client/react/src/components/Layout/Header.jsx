import React from "react";
import { Link, useNavigate } from "react-router-dom";
import user from "../../assets/img/user.png";

/*
작성자: 이유영
날짜(수정포함): 2023-09-12
설명: 로그인 마이페이지 이동
*/
function Header() {
  const navigator = useNavigate();

  const handleNav = () => {
    if (localStorage.getItem("accessToken") === null) {
      navigator("/Login");
    } else {
      navigator("/MyPage");
    }
  };
  return (
    <div
      className=" text-sm sm:text-base md:text-lg 
        lg:text-xl xl:text-xl text-custom-white p-3 sm:p-4 flex 
        justify-end items-center absolute w-full bg-custom-blue z-50 "
    >
      <Link to="/dashboard" className="mr-5">
        <button className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded">관리자페이지</button>
      </Link>

      <img onClick={handleNav} src={user} className=" justify-right max-h-[4vh] mt-2 sm:max-h-[4vh] object-contain" />
    </div>
  );
}

export default Header;
