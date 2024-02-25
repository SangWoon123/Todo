const routes = [
  // {
  //   path: '/',
  //   component: () => import('layouts/MainLayout.vue'),
  //   children: [
  //     { path: '', component: () => import('pages/IndexPage.vue') }
  //   ]
  // },
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "sign", component: () => import("pages/auth/SignIn.vue") },
      { path: "home", component: () => import("pages/TodoApp.vue") },
      { path: "home/redirect", component: () => import("src/pages/auth/RedirectPage.vue") },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
