import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../components/HelloWorld'

import SignUpPage from '../pages/SignUp'
import SignInPage from '../pages/SignIn'
import HomePage from '../pages/Home'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/signup',
      name: 'SignUpPage',
      component: SignUpPage
    },
    {
      path: '/',
      name: 'SignUpPage',
      component: SignInPage
    },
    {
      path: '/home',
      name: 'home',
      component: HomePage
    }
  ]
})
