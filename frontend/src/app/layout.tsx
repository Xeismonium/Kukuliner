import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  metadataBase: new URL(`${process.env.NEXT_PUBLIC_BASE_URL}`),
  title: "Kukuliner",
  description:
    "Kukuliner is your personal AI foodie, offering personalized food recommendations based on your tastes and location. Discover new dishes, find the best local restaurants, and enjoy a seamless dining experience with Kukuliner. Download now and let every meal be an unforgettable adventure!",
  twitter: {
    card: "summary_large_image",
  },
  openGraph: {
    title: "Kukuliner",
    description:
      "Kukuliner is your personal AI foodie, offering personalized food recommendations based on your tastes and location. Discover new dishes, find the best local restaurants, and enjoy a seamless dining experience with Kukuliner. Download now and let every meal be an unforgettable adventure!",
    type: "website",
    locale: "en-US",
    url: `${process.env.NEXT_PUBLIC_BASE_URL}`,
    siteName: "Kukuliner",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={inter.className}>{children}</body>
    </html>
  );
}
