import React from "react";
import WelcomeBanner from "../../partials/dashboard/WelcomeBanner";
import DashboardCard07 from "../../partials/dashboard/DashboardCard07";
import DashboardCard10 from "../../partials/dashboard/DashboardCard10";

const MainContent = () => {
  return (
    <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden">
      <main>
        <div className="px-4 sm:px-6 lg:px-8 py-14 w-full max-w-9xl mx-auto my-6">
          {/* Welcome banner */}
          <WelcomeBanner />

          {/* Cards */}
          <div className="grid grid-cols-12 gap-6">
            {/* Table (Top Channels) */}
            <DashboardCard07 />
            {/* Card (Customers) */}
            <DashboardCard10 />
          </div>
        </div>
      </main>
    </div>
  );
};

export default MainContent;
