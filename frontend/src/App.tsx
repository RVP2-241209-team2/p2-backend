import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";

// Layout Components
const AuthLayout = () => (
  <div className='bg-rose-200 min-h-screen'>
    <Outlet />
  </div>
);

const UserLayout = () => (
  <div className='bg-sky-200 min-h-screen'>
    <Outlet />
  </div>
);

const ShopOwnerLayout = () => (
  <div className='bg-amber-200 min-h-screen'>
    <Outlet />
  </div>
);

// pages
const HomePage = () => <div>Home Page</div>;
const ProductsPage = () => <div>Products Page</div>;
const ProductDetailPage = () => <div>Product Detail Page</div>;
const CartPage = () => <div>Cart Page</div>;
const CheckoutPage = () => <div>Checkout Page</div>;
const LoginPage = () => <div>Login Page</div>;
const RegisterPage = () => <div>Register Page</div>;
const UserPage = () => <div>User Page</div>;
const FAQPage = () => <div>FAQ Page</div>;
const ShopOwnerDashboardPage = () => <div>Shop Owner Dashboard Page</div>;
const ShopOwnerProductsPage = () => <div>Shop Owner Products Page</div>;
const ShopOwnerProductDetailPage = () => (
  <div>Shop Owner Product Detail Page</div>
);

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Auth routes */}
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
        </Route>
        {/* Shop owner routes */}
        <Route path="/shop-owner" element={<ShopOwnerLayout />}>
          <Route index element={<ShopOwnerDashboardPage />} />
          <Route path="products" element={<ShopOwnerProductsPage />} />
          <Route path="products/:id" element={<ShopOwnerProductDetailPage />} />
        </Route>

        {/* User/Public routes */}
        <Route element={<UserLayout />}>
          <Route path="/" element={<HomePage />} />
          <Route path="/products" element={<ProductsPage />} />
          <Route path="/products/:id" element={<ProductDetailPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/checkout" element={<CheckoutPage />} />
          <Route path="/user" element={<UserPage />} />
          <Route path="/faq" element={<FAQPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
