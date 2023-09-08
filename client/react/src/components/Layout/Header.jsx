import React from "react";
import { Link } from "react-router-dom";
import user from "../../assets/img/user.png";

function Header() {
  return (
    <div
      className=" text-sm sm:text-base md:text-lg 
        lg:text-xl xl:text-xl text-custom-white p-3 sm:p-4 flex 
        justify-end items-center absolute w-full"
    >
      <Link to="/dashboard" className="mr-5">
        <button className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded">
          관리자페이지
        </button>
      </Link>
      <Link to="/Login">
        <img
          src={user}
          className=" justify-right max-h-[4vh] mt-2 sm:max-h-[4vh] object-contain"
        />
      </Link>
    </div>
  );
}

export default Header;
