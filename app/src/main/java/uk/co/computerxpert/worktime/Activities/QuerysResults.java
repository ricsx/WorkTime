package uk.co.computerxpert.worktime.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.computerxpert.worktime.R;
import uk.co.computerxpert.worktime.data.model.FullQuerys;
import uk.co.computerxpert.worktime.data.repo.FullQuerysRepo;

import static uk.co.computerxpert.worktime.Common.Common.dformat;
import static uk.co.computerxpert.worktime.Common.Common.settingTest;


public class QuerysResults extends AppCompatActivity implements View.OnClickListener {

    Intent Uj_activity;
    private String newSelectQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querys_results);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        newSelectQuery = getIntent().getStringExtra("sel");
        Toolbar myToolbar = findViewById(R.id.querys_result_top);

        setSupportActionBar(myToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        addHeaders();
        addData();

        floatingActionButton();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setChecked(true);
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor, int txtsize) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(10, 10, 10, 10);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setTextSize(12);
        tv.setOnClickListener(this);
        tv.setGravity(1);
        if(txtsize != 0) tv.setTextSize(txtsize);
        return tv;
    }

    @NonNull
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(6, 6, 0, 0);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/

    public void addHeaders() {
        int cnt=0;
        TableLayout tl = findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.setBackgroundColor(Color.WHITE);
        if(settingTest("view_dayNameValues").equals("true")){
            tr.addView(getTextView(0, "DAY", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background),16)); cnt++;
        }
        if(settingTest("view_shiftValues").equals("true")) {
            tr.addView(getTextView(1, "SHIFT", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        if(settingTest("view_numberOfWeekValues").equals("true")) {
            tr.addView(getTextView(2, "WEEK", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        if(settingTest("view_paidHoursValues").equals("true")) {
            tr.addView(getTextView(3, "HOURS", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        if(settingTest("view_wageValues").equals("true")) {
            tr.addView(getTextView(4, "WAGE", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        if(settingTest("view_unpBreakValues").equals("true")) {
            tr.addView(getTextView(5, "UNP.BREAK", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        if(settingTest("view_companiesValues").equals("true")) {
            tr.addView(getTextView(6, "COMPANY", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        if(settingTest("view_agenciesValues").equals("true")) {
            tr.addView(getTextView(7, "AGENCY", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        if(settingTest("view_commentsValues").equals("true")) {
            tr.addView(getTextView(8, "COMMENTS", ContextCompat.getColor(this, R.color.toolbar_title), Typeface.BOLD, ContextCompat.getColor(this, R.color.toolbar_background), 16));
            cnt++;
        }
        tl.addView(tr, getTblLayoutParams());
        if(cnt==0) Toast.makeText(this, getString(R.string.noViewvableFields), Toast.LENGTH_LONG).show();
    }


    public void addData() {

        double hoursOfWeek=0;
        double salaryOfWeek=0;
        Integer rowcolor, cnt=0;
        String titleLine;
        String titleSumLine = "";

        TableLayout tl = findViewById(R.id.table);

        List<FullQuerys> fullQuerys_s = FullQuerysRepo.getFullQuerys(newSelectQuery);

        for(int i=0; i<fullQuerys_s.size();i++){
            cnt=0;
            hoursOfWeek = hoursOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_hours());
            salaryOfWeek = salaryOfWeek + Double.parseDouble(fullQuerys_s.get(i).getwt_salary());
            if (!fullQuerys_s.get(i).getwt_otwage().equals("0")) {
                rowcolor = R.color.row_overtime;
            } else if (!fullQuerys_s.get(i).get_wt_holiday().equals("0")) {
                rowcolor = R.color.row_holiday;
            } else {
                rowcolor = R.color.toolbar_background;
            }
            if(settingTest("beforevalues").equals("true")){
                titleLine = settingTest("currency")+" "+fullQuerys_s.get(i).getwt_salary()+"\n";
                titleSumLine = settingTest("currency")+" "+dformat.format(salaryOfWeek);

            }else{
                titleLine = fullQuerys_s.get(i).getwt_salary()+" "+settingTest("currency")+"\n";
                titleSumLine = dformat.format(salaryOfWeek)+" "+settingTest("currency");
            }
            String shift = fullQuerys_s.get(i).getwt_strsdate()+" - "+fullQuerys_s.get(i).getwt_strstime()+"\n"+
                    fullQuerys_s.get(i).getwt_stredate()+" - "+fullQuerys_s.get(i).getwt_stretime();

            Date date = new Date((long) fullQuerys_s.get(i).getwt_startdate()*1000L);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EE");
            String dayOfTheWeek = sdf.format(date);
            dayOfTheWeek = dayOfTheWeek.replace(" ","");
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.setBackgroundColor(Color.WHITE);
                if(settingTest("view_dayNameValues").equals("true")) {
                    tr.addView(getTextView(i + 1, dayOfTheWeek+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, rowcolor),0)); cnt++; }
                if(settingTest("view_shiftValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(), shift, ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++; }
                if(settingTest("view_numberOfWeekValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(), fullQuerys_s.get(i).getwt_week()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++; }
                if(settingTest("view_paidHoursValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(), fullQuerys_s.get(i).getwt_hours()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++; }
                if(settingTest("view_wageValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(),titleLine, ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++; }
                if(settingTest("view_unpBreakValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(),fullQuerys_s.get(i).getwt_unpbr()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++; }
                if(settingTest("view_companiesValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(),fullQuerys_s.get(i).getcomp_name()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++; }
                if(settingTest("view_agenciesValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(),fullQuerys_s.get(i).get_agency_name()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++;}
                if(settingTest("view_commentsValues").equals("true")) {
                    tr.addView(getTextView(i + fullQuerys_s.size(),fullQuerys_s.get(i).getwt_rem()+"\n", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, rowcolor),0)); cnt++; }

            tl.addView(tr, getTblLayoutParams());
        }
        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(Color.WHITE);
        tr.addView(getTextView(1, "", ContextCompat.getColor(this, R.color.text_color), Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1, "TOTAL: ", ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1,  ""+hoursOfWeek+" "+getString(R.string.hours), ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tr.addView(getTextView(1,  titleSumLine, ContextCompat.getColor(this, R.color.text_color), Typeface.BOLD, ContextCompat.getColor(this, R.color.colorWhite),16));
        tl.addView(tr, getTblLayoutParams());
        if(titleSumLine.equals("")) Toast.makeText(this, getString(R.string.noDataMatching), Toast.LENGTH_LONG).show();
    }


    public void printPDF(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        assert printManager != null;
        printManager.print("printJobOutput", new ViewPrintAdapter(this, findViewById(R.id.horizQueryScroll)), null);
    }

    public class ViewPrintAdapter extends PrintDocumentAdapter {

        private PrintedPdfDocument mDocument;
        private Context mContext;
        private View mView;

        ViewPrintAdapter(Context context, View view) {
            mContext = context;
            mView = view;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback, Bundle extras) {

            mDocument = new PrintedPdfDocument(mContext, newAttributes);

            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }

            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(1);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        }

        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                            CancellationSignal cancellationSignal,
                            WriteResultCallback callback) {

            PdfDocument.Page page = mDocument.startPage(0);
            int h = 0, w = 0;
            Bitmap bitmap;
            HorizontalScrollView scrollView = (HorizontalScrollView) mView;

            for (int i = 0; i < scrollView.getChildCount(); i++) {
                h += scrollView.getChildAt(i).getHeight();
                w += scrollView.getChildAt(i).getWidth();
                scrollView.getChildAt(i).setBackgroundResource(R.color.colorAccent);
            }

            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);

            Rect src = new Rect(0, 0, w, h);

            Canvas pageCanvas = page.getCanvas();
            float pageWidth = pageCanvas.getWidth();
            float pageHeight = pageCanvas.getHeight();

            float scale = Math.min(pageWidth/src.width(), pageHeight/src.height());
            float left = pageWidth / 2 - src.width() * scale / 2;
            float top = pageHeight / 2 - src.height() * scale / 2;
            float right = pageWidth / 2 + src.width() * scale / 2;
            float bottom = pageHeight / 2 + src.height() * scale / 2;
            RectF dst = new RectF(left, top, right, bottom);

            pageCanvas.drawBitmap(bitmap, src, dst, null);
            mDocument.finishPage(page);

            try {
                mDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                mDocument.close();
                mDocument = null;
            }
            callback.onWriteFinished(new PageRange[]{new PageRange(0, 0)});
        }
    }


    public void floatingActionButton(){
        FloatingActionButton fab = findViewById(R.id.fab4);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                printPDF(findViewById(R.id.horizQueryScroll));
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv = findViewById(id);
        if (null != tv) {
            Toast.makeText(this, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Uj_activity = new Intent(QuerysResults.this, Querys.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_dashboard:
                    Uj_activity = new Intent(QuerysResults.this, MainActivity.class);
                    startActivity(Uj_activity);
                    return true;
                case R.id.navigation_notifications:
                    Uj_activity = new Intent(QuerysResults.this, Setup.class);
                    startActivity(Uj_activity);
                    return true;
            }
            return false;
        }
    };

}