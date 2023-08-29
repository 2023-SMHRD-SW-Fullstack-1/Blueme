import React from "react";
import { Link } from "react-router-dom";
import "../index.css";

const Join = () => {
  const years = Array.from({ length: 121 }, (_, i) => new Date().getFullYear() - i);
  const months = Array.from({ length: 12 }, (_, i) => i + 1);
  const days = Array.from({ length: 31 }, (_, i) => i + 1);
  return (
    <div className="bg-custom-blue flex flex-col justify-center items-center">
      <div className="relative mb-6">
        <a
          className="mb-3 flex w-full items-center justify-center rounded bg-primary px-7 pb-2.5 pt-3 text-center bg-custom-blue text-custom-white font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
          // style="background-color: #3b5998"
          href="#!"
          role="button"
          data-te-ripple-init
          data-te-ripple-color="light"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="mr-2 h-3.5 w-3.5" fill="currentColor" viewBox="0 0 24 24">
            <path d="M9 8h-3v4h3v12h5v-12h3.642l.358-4h-4v-1.667c0-.955.192-1.333 1.115-1.333h2.885v-5h-3.808c-3.596 0-5.192 1.583-5.192 4.615v3.385z" />
          </svg>
          Continue with KakaoTalk
        </a>
        <a
          className="mb-3 flex w-full items-center justify-center bg-custom-blue rounded bg-info px-7 pb-2.5 pt-3 text-center text-sm font-medium uppercase leading-normal text-custom-white shadow-[0_4px_9px_-4px_#54b4d3] transition duration-150 ease-in-out hover:bg-info-600 hover:shadow-[0_8px_9px_-4px_rgba(84,180,211,0.3),0_4px_18px_0_rgba(84,180,211,0.2)] focus:bg-info-600 focus:shadow-[0_8px_9px_-4px_rgba(84,180,211,0.3),0_4px_18px_0_rgba(84,180,211,0.2)] focus:outline-none focus:ring-0 active:bg-info-700 active:shadow-[0_8px_9px_-4px_rgba(84,180,211,0.3),0_4px_18px_0_rgba(84,180,211,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(84,180,211,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(84,180,211,0.2),0_4px_18px_0_rgba(84,180,211,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(84,180,211,0.2),0_4px_18px_0_rgba(84,180,211,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(84,180,211,0.2),0_4px_18px_0_rgba(84,180,211,0.1)]"
          // style="background-color: #55acee"
          href="#!"
          role="button"
          data-te-ripple-init
          data-te-ripple-color="light"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="mr-2 h-3.5 w-3.5" fill="currentColor" viewBox="0 0 24 24">
            <path d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z" />
          </svg>
          Continue with Google
        </a>

        <div className="text-custom-white my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300">
          <p className="mx-4 mb-0 text-center font-semibold text-custom-white">OR</p>
        </div>
        <div className="text-xl text-custom-white mg-5 flex justify-center">회원가입</div>
      </div>

      <div className="">
        <div className="text-custom-black relative mb-6" data-te-input-wrapper-init>
          <div className="text-custom-white my-1">Email Address</div>
          <input
            type="email"
            className="peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="Email address"
          />
        </div>
        <div className=" relative mb-6" data-te-input-wrapper-init>
          <div className="text-custom-white my-1"> Password</div>
          <input
            type="password"
            className="peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="Password"
          />
        </div>
        <div className="relative mb-6" data-te-input-wrapper-init>
          <div className="text-custom-white my-1">Confirm Password</div>
          <input
            type="password"
            className="peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="Confirm Password"
          />
        </div>
        <div className="relative mb-6" data-te-input-wrapper-init>
          <div className="text-custom-white my-1">NickName</div>
          <input
            type="email"
            className="peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="Email address"
          />
        </div>
        <div className="flex-row text-center text-custom-white my-4 pr-10">
          <span className="mx-2 pr-1">연도</span>
          <span className="mx-[40px] pr-7">월</span>
          <span className="mx-2 pr-3">일</span>
        </div>

        <div className="flex justify-around text-sm mt-4">
          <select
            name="year"
            id="year"
            className="w-1/3 bg-transparent text-custom-black mr-5 rounded border border-custom-white px-2 py-1"
          >
            {years.map((year, index) => (
              <option key={index} value={year}>
                {year}
              </option>
            ))}
          </select>

          <select
            name="month"
            id="month"
            className="w-1/3 bg-transparent text-custom-black mr-5 rounded border border-custom-white px-2 py-1"
          >
            {months.map((month, index) => (
              <option key={index} value={month}>
                {month}
              </option>
            ))}
          </select>

          <select
            name="day"
            id="day"
            className="w-1/3 bg-transparent text-custom-black rounded border border-custom-white px-2 py-1"
          >
            {days.map((day, index) => (
              <option key={index} value={day}>
                {day}
              </option>
            ))}
          </select>
        </div>
        <div className="my-5">
          <button
            type="submit"
            className="bg-custom-blue inline-block w-full rounded bg-primary px-7 pb-2.5 pt-3 text-sm font-medium uppercase leading-normal  text-custom-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
            data-te-ripple-init
            data-te-ripple-color="light"
          >
            Sign Up
          </button>
        </div>
      </div>
    </div>
  );
};

export default Join;
