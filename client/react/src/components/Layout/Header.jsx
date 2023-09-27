import React from "react";
import { Link, useNavigate } from "react-router-dom";
import user from "../../assets/img/user.png";
import { useSelector } from "react-redux";



/*
작성자: 이유영
날짜(수정포함): 2023-09-12
설명: 로그인 마이페이지 이동
*/
/*
작성자: 김혁
날짜(수정포함): 2023-09-26
설명: 관리자페이지 관리자역할만 보이도록 수정
*/
function Header() {
  const navigator = useNavigate();
  const isLogin = useSelector(state => state.memberReducer.isLogin)
  const userRole = useSelector(state => state.memberReducer.user.role);

  const handleNav = () => {
    if (isLogin === false) {
      navigator("/Login");
    } else {
      navigator("/MyPage");
    }
  };

  // isLogin이 true이고 userRole이 'ADMIN'인 경우에만 버튼을 추가
  const adminButton = isLogin && userRole === 'ADMIN' ? (
    <Link to="/dashboard" className="mr-5">
      <button className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded">
        관리자페이지
      </button>
    </Link>
  ) : null;

  return (
    <div
      className=" text-sm sm:text-base md:text-lg 
        lg:text-xl xl:text-xl text-custom-white p-2 sm:p-4 flex 
        justify-end items-center fixed w-full bg-custom-blue z-50 "
    >
      {adminButton}
      <img onClick={handleNav} src={user} className=" justify-right max-h-[4vh] mt-2 sm:max-h-[4vh] object-contain" />
    </div>
  );
}

export default Header;
