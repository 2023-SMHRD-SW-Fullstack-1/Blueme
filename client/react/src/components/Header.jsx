import React from "react";
import { Link } from "react-router-dom";

function Header() {
  return (
    <div className="bg-custom-blue text-custom-white">
      <Link to="/Login">로그인</Link>
    </div>
  );
}

export default Header;
