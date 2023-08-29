import React from "react";
import { Link } from "react-router-dom";

function Header() {
  return (
    <div className="bg-custom-blue text-sm sm:text-base md:text-lg lg:text-xl xl:text-xl text-custom-white p-3 sm:p-4">
      <Link to="/Login">로그인</Link>
    </div>
  );
}

export default Header;
