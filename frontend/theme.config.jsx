export default {
  docsRepositoryBase: "https://github.com/Xeismonium/Kukuliner", // base URL for the docs repository
  logo: <span>Kukuliner Documentation</span>,
  project: {
    link: "https://github.com/Xeismonium/Kukuliner",
  },
  darkMode: false,

  useNextSeoProps() {
    return {
      titleTemplate: "%s – Kukuliner",
    };
  },

  editLink: {
    component: null,
  },

  footer: {
    text: (
      <span>
        MIT {new Date().getFullYear()} ©{" "}
        <a href="https://github.com/Xeismonium/Kukuliner/" target="_blank">
          Kukuliner
        </a>
        .
      </span>
    ),
  },

  head: (
    <>
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <meta property="title" content="API References - Kukuliner" />
      <meta
        property="description"
        content="Explore our comprehensive API reference documentation. Find detailed guides, code examples, and best practices to integrate and utilize our APIs effectively."
      />
      <meta property="og:title" content="API References - Kukuliner" />
      <meta
        property="og:description"
        content="Explore our comprehensive API reference documentation. Find detailed guides, code examples, and best practices to integrate and utilize our APIs effectively."
      />
    </>
  ),
};
