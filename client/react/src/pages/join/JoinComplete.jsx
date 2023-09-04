import React from 'react';
import { Link } from 'react-router-dom';

const JoinComplete = () => {
    return (
        <div className="bg-custom-blue text-custom-white text-center flex flex-col min-h-screen  justify-center items-center text-2xl">
            Blueme 회원가입을 축하드립니다.
            <Link to="/">
                <span className="text-sm text-custom-gray pt-3"> 홈 화면으로</span>
            </Link>
        </div>
    );
};

export default JoinComplete;
