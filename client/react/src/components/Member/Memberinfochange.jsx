import React from "react";
import { Link } from "react-router-dom";

const Memberinfochange = () => {
  return (
    <div className="bg-custom-blue flex flex-col justify-center items-center text-custom-white min-h-screen">
      <div className="text-2xl mb-5">회원정보수정</div>
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
          className="text-custom-black peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
          placeholder="Password"
        />
      </div>
      <div className="relative mb-6" data-te-input-wrapper-init>
        <div className="text-custom-white my-1">Confirm Password</div>
        <input
          type="password"
          className="text-custom-black peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
          placeholder="Confirm Password"
        />
      </div>
      <div className="relative mb-6" data-te-input-wrapper-init>
        <div className="text-custom-white my-1">NickName</div>
        <input
          type="email"
          className="text-custom-black peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
          placeholder="NickName"
        />
      </div>
      <div className="">
        <button
          type="submit"
          className=" bg-custom-blue inline-block w-full rounded bg-primary px-7 pb-2.5 pt-3 text-sm font-medium uppercase leading-normal  text-custom-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
          data-te-ripple-init
          data-te-ripple-color="light"
        >
          Change
        </button>
      </div>
    </div>
  );
};

export default Memberinfochange;
