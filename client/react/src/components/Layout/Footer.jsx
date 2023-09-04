import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome, faSearch, faBookOpen, faThumbsUp, faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

function Footer() {
  return (
    <div className="bg-custom-blue text-custom-white p-3 sm:p-4 flex justify-between items-center ">
      <Link to="/" className="flex flex-col items-center w-full text-center">
        <FontAwesomeIcon icon={faHome} size="2x" />
        <button>홈</button>
      </Link>

      <Link to="/" className="flex flex-col items-center w-full text-center">
        <FontAwesomeIcon icon={faThumbsUp} size="2x" />
        <button>추천</button>
      </Link>

      <Link to="/Themerecommend" className="flex flex-col items-center w-full text-center">
        <FontAwesomeIcon icon={faMagnifyingGlass} size="2x" />
        <button>테마</button>
      </Link>

      <Link to="/library" className="flex flex-col items-center w-full text-center">
        <FontAwesomeIcon icon={faBookOpen} size="2x" />
        <button>라이브러리</button>
      </Link>
    </div>
  );
}

export default Footer;
