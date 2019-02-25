import Vue from 'vue'
import Router from 'vue-router'

import SignUpPage from '../pages/customer/SignUp'
import SignInPage from '../pages/customer/SignIn'
import HomePage from '../pages/customer/Home'
import OrderPage from '../pages/customer/OrderPage'
import AddressPage from '../pages/customer/AddressPage'
import SellerDetailPage from '../pages/customer/SellerDetail'
import PersonalPage from '../pages/customer/PersonalInfo'
import EditPersonalInfoPage from '../pages/customer/EditPersonalInfo'
import CustomerStatisticPage from '../pages/customer/CustomerStatistic'
import CheckOutPage from '../pages/customer/CheckOut'

import SellerSignInPage from '../pages/seller/SellerSignIn'
import SellerSignUpPage from '../pages/seller/SellerSignUp'
import SellerHomePage from '../pages/seller/SellerHome'
import SellerPromptPage from '../pages/seller/SellerSignPrompt'
import SellerOrderPage from '../pages/seller/SellerOrder'
import SellerStatisticPage from '../pages/seller/SellerStatistic'
import SellerInfoPage from '../pages/seller/SellerInfo'
import SellerInProcessPage from '../pages/seller/SellerInProcess'
import SellerEditPage from '../pages/seller/SellerEdit'
import SellerReEditPage from '../pages/seller/SellerReEdit'

import AdminStatisticPage from '../pages/admin/AdminStatistic'
import VerifySellerPage from '../pages/admin/VerifySeller'
import AdminCostPage from '../pages/admin/AdminCost'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/signUp',
      name: 'SignUpPage',
      component: SignUpPage
    },
    {
      path: '/',
      name: 'SignInPage',
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
    },
    {
      path: '/info',
      name: 'info',
      component: PersonalPage
    },
    {
      path: '/confirm',
      name: 'confirm',
      component: EditPersonalInfoPage
    },
    {
      path: '/customer/statistic',
      name: 'customerStatistic',
      component: CustomerStatisticPage
    },
    {
      path: '/checkout',
      name: 'checkout',
      component: CheckOutPage
    },

    {
      path: '/seller/signIn',
      name: 'sellerSignIn',
      component: SellerSignInPage
    },
    {
      path: '/seller/home',
      name: 'sellerHome',
      component: SellerHomePage
    },
    {
      path: '/seller/signUp',
      name: 'sellerSignUp',
      component: SellerSignUpPage
    },
    {
      path: '/seller/prompt',
      name: 'sellerPrompt',
      component: SellerPromptPage
    },
    {
      path: '/seller/order',
      name: 'sellerOrder',
      component: SellerOrderPage
    },
    {
      path: '/seller/statistic',
      name: 'sellerStatistic',
      component: SellerStatisticPage
    },
    {
      path: '/seller/info',
      name: 'sellerInfo',
      component: SellerInfoPage
    },
    {
      path: '/seller/process',
      name: 'sellerInProcess',
      component: SellerInProcessPage
    },
    {
      path: '/seller/edit',
      name: 'sellerEdit',
      component: SellerEditPage
    },
    {
      path: '/seller/reEdit',
      name: 'sellerReEdit',
      component: SellerReEditPage
    },
    {
      path: '/admin/verify',
      name: 'adminVerify',
      component: VerifySellerPage
    },
    {
      path: '/admin/cost',
      name: 'adminCost',
      component: AdminCostPage
    },
    {
      path: '/admin/statistic',
      name: 'adminStatistic',
      component: AdminStatisticPage
    }
  ]
})
