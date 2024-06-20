import Image from "next/image";
import Link from "next/link";
import Logo from "../../../public/logo.png";
import React from "react";

export default function Footer() {
  return (
    <div>
      <div className="border-t border-neutral-100 dark:border-white/[0.1] px-8 py-20">
        <div className="max-w-7xl mx-auto text-sm text-neutral-500 flex sm:flex-row flex-col justify-between items-start">
          <div>
            <div className="mr-4  md:flex mb-4">
              <Link
                className="flex items-center justify-center space-x-2 text-2xl font-bold text-center text-neutral-600 dark:text-gray-100 selection:bg-emerald-500 mr-10 py-0"
                href="/"
              >
                <div className="relative h-8 w-8 md:h-6 md:w-6 bg-black border border-slate-800  text-white   flex items-center justify-center rounded-md text-sm antialiased">
                  <div className="absolute h-10 w-full bg-white/[0.2] -top-10 inset-x-0 rounded-full blur-xl"></div>
                  <div className="text-sm  text-emerald-500 relative z-20">
                    <Image
                      src={Logo}
                      alt="Logo"
                      loading="lazy"
                      width={50}
                      height={50}
                      decoding="async"
                    />
                  </div>
                </div>
                <div className="flex flex-col">
                  <h1 className="text-black dark:text-white font-sans">
                    {" "}
                    Kukuliner
                  </h1>
                </div>
              </Link>
            </div>
            <div>
              A product by{" "}
              <Link
                target="__blank"
                className="dark:text-sky-500 text-neutral-600 font-medium"
                href="/"
              >
                Team C241-PS080
              </Link>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-10 items-start mt-10 md:mt-0">
            <div className="flex justify-center space-y-4 flex-col mt-2">
              <Link
                target="__blank"
                className="transition-colors hover:text-foreground/80 text-foreground/60"
                href="https://github.com/Xeismonium/Kukuliner/"
              >
                GitHub
              </Link>
            </div>
            <div className="flex justify-center space-y-4 flex-col mt-2">
              <Link
                target="__blank"
                className="transition-colors hover:text-foreground/80 text-foreground/60"
                href="https://grow.google/intl/id_id/bangkit/"
              >
                Bangkit
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
