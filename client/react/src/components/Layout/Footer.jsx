import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faHome,
    faSearch,
    faBookOpen,
    faThumbsUp,
    faMagnifyingGlass,
    faHashtag,
} from '@fortawesome/free-solid-svg-icons';

function Footer() {
    return (
        <div
            className="bg-custom-blue text-custom-white p-3 sm:p-4 
        flex justify-between items-center absolute w-full bottom-0"
        >
            <Link to="/" className="flex flex-col items-center w-full text-center">
                <FontAwesomeIcon icon={faHome} size="2x" />
                <button className="text-xs">홈</button>
            </Link>

            <Link to="/" className="flex flex-col items-center w-full text-center">
                <FontAwesomeIcon icon={faThumbsUp} size="2x" />
                <button className="text-xs">추천</button>
            </Link>

            <Link to="/Themerecommend" className="flex flex-col items-center w-full text-center">
                <FontAwesomeIcon icon={faHashtag} size="2x" />
                <button className="text-xs">테마</button>
            </Link>

            <Link to="/library" className="flex flex-col items-center w-full text-center">
                <FontAwesomeIcon icon={faBookOpen} size="2x" />
                <button className="text-xs">라이브러리</button>
            </Link>
        </div>
    );
}

export default Footer;
