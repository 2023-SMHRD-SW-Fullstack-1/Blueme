import React, { useState } from "react";
import axios from "axios";

const MemberDelete = () => {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [passwordErrorModalIsOpen, setPasswordErrorModalIsOpen] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  // 탈퇴하기 버튼 클릭 시 실행되는 함수
  const handleDelete = () => {
    setModalIsOpen(true);
  };

  // 모달 닫기 함수
  const closeModal = () => {
    setModalIsOpen(false);
    setEmail("");
    setPassword("");
    setConfirmPassword("");
    setPasswordErrorModalIsOpen(false);
  };

  const handleConfirmDelete = async () => {
    if (password === confirmPassword) {
      try {
        const response = await axios.delete("user/deactivate", { data: { email: email, password: password } });

        console.log(response);
        console.log("Sending request with email:", email);
        console.log("Sending request with email:", password);

        alert("회원 탈퇴가 성공적으로 완료되었습니다.");
      } catch (error) {
        console.error(error);
        alert("회원 탈퇴에 실패했습니다.");
      }

      closeModal();
    } else {
      setPasswordErrorModalIsOpen(true);
    }
  };
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 flex flex-col min-h-screen text-xl font-semibold tracking-tighter">
      <p className="mt-[240px] text-xl text-center ">탈퇴를 진행하시려면 비밀번호를 입력해주세요.</p>
      <input
        type="email"
        onChange={(e) => setEmail(e.target.value)}
        className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] h-[45px] text-base focus:border-custom-white pl-2 w-full mt-[60px] rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
        placeholder="이메일을 입력해주세요."
      />
      <input
        type="password"
        onChange={(e) => setPassword(e.target.value)}
        className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] h-[45px] text-base focus:border-custom-white pl-2 w-full mt-8 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
        placeholder="비밀번호를 입력해주세요."
      />
      <input
        type="password"
        onChange={(e) => setConfirmPassword(e.target.value)}
        className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] h-[45px] text-base focus:border-custom-white pl-2 w-full mt-8 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
        placeholder="비밀번호를 다시 입력해주세요."
      />
      <button
        onClick={handleDelete}
        className="
        mt-12
        w-full
        px-3 h-[43px] relative 
        bg-[#221a38]  
        rounded-lg border border-soild border-[#fdfdfd]
        text-custom-white
        text-[16px]"
      >
        탈퇴하기
      </button>
      {/* 모달 */}
      <div
        id="popup-modal"
        tabIndex="-1"
        className={`fixed top-0 left-0 right-0 bottom-0 z-50 p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] max-h-full ${
          modalIsOpen ? "" : "hidden"
        } flex items-center justify-center`}
      >
        <div class="flex justify-center w-full max-w-md max-h-full">
          <div class=" bg-custom-blue  border rounded-lg shadow dark:bg-gray-700">
            <button
              type="button"
              class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ml-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
              data-modal-hide="popup-modal"
            >
              <svg
                class="w-3 h-3"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 14 14"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                />
              </svg>
              <span class="sr-only">Close modal</span>
            </button>
            <div class="p-6 text-center">
              <svg
                class="mx-auto mb-4 text-gray-400 w-40 h-10 dark:text-gray-200"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 20 20"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                />
              </svg>
              <h3 class="mb-5 text-lg font-normal text-gray-400 dark:text-gray-400">정말 탈퇴하시겠습니까?</h3>
              <button
                onClick={handleConfirmDelete}
                data-modal-hide="popup-modal"
                type="button"
                class="text-white bg-gray-500 hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2"
              >
                네 탈퇴할게요.
              </button>
              <button
                onClick={closeModal}
                data-modal-hide="popup-modal"
                type="button"
                class="text-white bg-gray-500 hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2"
              >
                아니요 안할래요.
              </button>
              {/* 비밀번호 오류 모달 */}
              <div
                id="password-error-modal"
                tabIndex="-1"
                className={`fixed top-0 left-0 right-0 bottom-0 z-50 p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] max-h-full ${
                  passwordErrorModalIsOpen ? "" : "hidden"
                } flex items-center justify-center`}
              >
                <div class="flex justify-center w-full max-w-md max-h-full">
                  <div class=" bg-custom-blue border-2 rounded-lg shadow dark:bg-gray-700">
                    <button
                      type="button"
                      onClick={() => setPasswordErrorModalIsOpen(false)}
                      class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ml-auto inline-flex justify-center items-center dark:hover:bg-gray"
                    ></button>
                    <div class="p-6 text-center">
                      <h3 class="mb-5 text-lg font-normal text-gray500 dark:text-gray400">비밀번호가 틀렸습니다.</h3>
                      <button
                        onClick={() => {
                          setPasswordErrorModalIsOpen(false);
                          setModalIsOpen(true); // 'OK' 버튼을 누르면 첫 번째 모달창이 다시 나타납니다.
                        }}
                        type="button"
                        class="
    mt-5 
    px-3 
    h-auto 
    bg-[#221a38]  
    rounded-lg border border-soild border-[#fdfdfd]
    text-custom-white"
                      >
                        OK
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MemberDelete;
