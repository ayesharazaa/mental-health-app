# Password Reset Feature - Testing Guide

## ✅ What Was Fixed

1. **Added View Controller Mappings**
   - `/forgot_password` → returns `forgot_password.html`
   - `/reset_password` → returns `reset_password.html`

2. **Updated Security Configuration**
   - Added `/forgot_password` and `/reset_password` to public access list

3. **Fixed URL Consistency**
   - Login page now links to `/forgot_password` (with underscore)
   - Email sends link to `http://localhost:8080/reset_password?token=xxx`

## 🧪 How to Test

### Step 1: Access Forgot Password Page
1. Open browser and go to: `http://localhost:8080/login`
2. Click "Forgot your password?" link
3. **Expected:** You should see the "Reset Password" page with an email input field
4. **If blank:** Check browser console (F12) for errors

### Step 2: Request Password Reset
1. Enter a valid email address (one that exists in your database)
2. Click "Send Reset Link"
3. **Expected:** Message "If the email exists, a reset link has been sent"
4. Check your email inbox for the reset email

### Step 3: Click Email Link
1. Open the email and click the reset link
2. The link should look like: `http://localhost:8080/reset_password?token=abc123...`
3. **Expected:** You should see "Set New Password" page with a password input field
4. **If blank/not loading:** 
   - Check if URL is correct (should have `reset_password` not `reset-password`)
   - Check browser console for errors
   - Verify app is running on port 8080

### Step 4: Reset Password
1. Enter a new password
2. Click "Update Password"
3. **Expected:** Success message, then redirect to login page after 1.5 seconds
4. **If error:** Check if token is expired (30 minutes validity)

### Step 5: Login with New Password
1. On login page, enter username and new password
2. Click "Sign In"
3. **Expected:** Successfully logged in and redirected to dashboard

## 🔍 Troubleshooting

### Issue: Blank Page on `/reset_password`
**Possible Causes:**
- CSS file not loading → Check browser console
- JavaScript error → Check browser console
- Template not found → Check server logs

**Solution:**
```bash
# Check if app is running
curl http://localhost:8080/reset_password

# Should return 200 status code
```

### Issue: "Page Not Found" (404)
**Possible Causes:**
- Application not running
- Wrong URL (using hyphen instead of underscore)

**Solution:**
- Ensure app is running on port 8080
- Use `/reset_password` not `/reset-password`

### Issue: Token Invalid or Expired
**Possible Causes:**
- Token expired (30 minute validity)
- Token already used
- Database connection issue

**Solution:**
- Request a new reset link
- Check server logs for errors

## 📋 Current Configuration

### API Endpoints
- `POST /auth/forgot-password` - Request password reset
- `POST /auth/reset-password` - Submit new password

### View Routes
- `GET /forgot_password` - Forgot password form
- `GET /reset_password` - Reset password form

### Email Link Format
```
http://localhost:8080/reset_password?token={UUID}
```

### Token Expiry
- 30 minutes from generation
- Automatic cleanup runs hourly

## ✅ Verification Checklist

- [ ] Application running on port 8080
- [ ] Can access `/login` page
- [ ] Can click "Forgot your password?" link
- [ ] Forgot password page loads with email input
- [ ] Can submit email and see success message
- [ ] Email received with reset link
- [ ] Reset link opens password reset page
- [ ] Can enter new password
- [ ] Password successfully updated
- [ ] Can login with new password

## 🔧 Quick Fixes

### If CSS not loading:
Check that `/css/users.css` exists at:
`src/main/resources/static/css/users.css`

### If template not found:
Check that templates exist at:
- `src/main/resources/templates/forgot_password.html`
- `src/main/resources/templates/reset_password.html`

### If security blocking:
Verify in `SecurityConfig.java`:
```java
.requestMatchers(
    "/forgot_password",
    "/reset_password",
    // ... other public paths
).permitAll()
```

## 📞 Need Help?

If you're still experiencing issues:
1. Check browser console (F12 → Console tab)
2. Check server logs in the terminal
3. Verify database connection
4. Ensure email service is configured correctly in `application.properties`
