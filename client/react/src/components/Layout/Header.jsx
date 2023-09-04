import React from "react";
import { Link } from "react-router-dom";
import logo from "../../img/logo.png";
import user from "../../img/user.png";

function Header() {
  return (
    <div className="bg-custom-blue text-sm sm:text-base md:text-lg lg:text-xl xl:text-xl text-custom-white p-3 sm:p-4 flex justify-end items-center">
      <Link to="/Login">
        <img
          src={user}
          className=" justify-right ml-2 h-auto max-h-[5vh] sm:max-h-[5vh] object-contain bg-custom-blue"
        />
      </Link>
    </div>
  );
}

export default Header;
