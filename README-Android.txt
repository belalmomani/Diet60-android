بناء تطبيق أندرويد (APK) من هذا المشروع
=========================================
1) ثبّتي Android Studio (النسخة الحديثة) من developer.android.com/studio
2) File > Open ثم اختاري هذا المجلد (Diet60-Android)
3) انتظري انتهاء Gradle Sync (يحتاج إنترنت أول مرة، وقد يطلب تنزيل Android SDK 34)
4) لتجربته: وصّلي هاتفك (تفعيل USB debugging) واضغطي Run ▶
5) لإنشاء APK للتثبيت: Build > Build Bundle(s) / APK(s) > Build APK(s)
   الملف يظهر في app/build/outputs/apk/debug/app-debug.apk
   انقليه لهاتفك وثبّتيه (فعّلي "التثبيت من مصادر غير معروفة").
6) لرفعه على Google Play: Build > Generate Signed Bundle / APK (يلزم مفتاح توقيع).

تغيير رابط الموقع: افتحي app/src/main/res/values/strings.xml وعدّلي start_url فقط.
إن كان موقعك يعمل بـ https غيّريه لـ http أو العكس حسب ما يفتح في المتصفح.
ملاحظة: التطبيق يعرض موقعك المرفوع، فأي تحديث للموقع يظهر تلقائيًا بدون إعادة بناء التطبيق.

=========================================
بناء الـ APK تلقائيًا عبر GitHub Actions (بدون تثبيت أي برنامج)
=========================================
هذا المجلد يحتوي بالفعل على .github/workflows/build-apk.yml الذي يبني الـ APK تلقائيًا.

1) أنشئي مستودع جديد على github.com (خاص Private أو عام، كما تفضّلين).
2) ارفعي محتوى هذا المجلد (Diet60-Android) بالكامل إلى المستودع — إما بسحب الملفات
   على صفحة المستودع في المتصفح (uploading files)، أو عبر git:
      git init
      git add .
      git commit -m "Diet60 Android app"
      git branch -M main
      git remote add origin <رابط المستودع>
      git push -u origin main
3) بعد الرفع، افتحي تبويب Actions في المستودع — سيبدأ البناء تلقائيًا (يأخذ نحو 3-5 دقائق).
   إن لم يبدأ: اضغطي على "Build Android APK" في القائمة اليسرى ثم "Run workflow".
4) عند اكتمال البناء (علامة ✓ خضراء)، افتحي التشغيل (run) ثم مرري لأسفل إلى
   قسم Artifacts واضغطي "diet60-debug-apk" لتنزيله كملف zip يحتوي على الـ APK.
5) فكّي الضغط، انقلي app-debug.apk لهاتفك، وثبّتيه (فعّلي "التثبيت من مصادر غير معروفة").

ملاحظة: هذا الـ APK للتجربة والتثبيت المباشر على هاتفك (غير موقّع لمتجر Google Play).
لرفعه على Google Play لاحقًا يلزم توقيعه بمفتاح release — أخبريني عند الحاجة لذلك.

