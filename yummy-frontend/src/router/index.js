import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../components/HelloWorld'

import SignUpPage from '../pages/SignUp'
import SignInPage from '../pages/SignIn'
import HomePage from '../pages/Home'
import OrderPage from '../pages/OrderPage'
import AddressPage from '../pages/AddressPage'
import SellerDetailPage from '../pages/SellerDetail'


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
    },
    {
      path: '/order',
      name: 'order',
      component: OrderPage
    },
    {
      path: '/address',
      name: 'address',
      component: AddressPage
    },
    {
      path: '/sellerDetail',
      name: 'sellerDetail',
      component: SellerDetailPage
    }
  ]
})
