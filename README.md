# RoundImageView
A Round ImageView that works with vectors!
Featuring:
- Works with vectors!
- Customisable border width
- Customisable border color
- Customisable icon color

[![](https://jitpack.io/v/thomhurst/RoundImageView.svg)](https://jitpack.io/#thomhurst/RoundImageView)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d3ea7602a9fe4eef986dc303bda6f250)](https://www.codacy.com/app/thomhurst/RoundImageView?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=thomhurst/RoundImageView&amp;utm_campaign=Badge_Grade)

## Install

Add Jitpack to your repositories in your `build.gradle` file

```groovy
allprojects {
    repositories {
      // ...
      maven { url 'https://jitpack.io' }
    }
}
```

Add the below to your dependencies, again in your gradle.build file

```groovy
implementation 'com.github.thomhurst:RoundImageView:{version}'
```

## Sample

<img src="https://github.com/thomhurst/RoundImageView/blob/master/images/sample.jpg" width="450"/>

## Comparison

<img src="https://github.com/thomhurst/RoundImageView/blob/master/images/comparison.png" width="450"/>

Just to say - Absolutely no problem with these libraries, they are great.

Just wasn't working for me when I wanted a vector. :)

## Code

```xml
<com.tomlonghurst.roundimageview.RoundImageView
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="150dp"
            android:layout_height="150dp"
            app:riv_border_width="2dp"
            app:riv_border_color="#000000">

            <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:src="@drawable/ic_baseline_sentiment_very_satisfied_24px"
                    android:tint="@android:color/black"
                    android:scaleType="fitXY"/>

    </com.tomlonghurst.roundimageview.RoundImageView>
```

OR

```xml
<com.tomlonghurst.roundimageview.RoundImageView
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="150dp"
            android:layout_height="150dp"
            app:riv_border_width="2dp"
            app:riv_border_color="#000000"
            app:riv_circle_placeholder_color="#000000"
            app:riv_circle_placeholder_drawable="@drawable/ic_baseline_sentiment_very_satisfied_24px">

        <ImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="fitXY"/>

    </com.tomlonghurst.roundimageview.RoundImageView>
```

### Attributes
```
riv_border_width > Dimension/Size
riv_border_color > Color ID
riv_circle_background_color > Color ID
riv_circle_placeholder_color > Color ID
riv_circle_placeholder_drawable > Drawable ID
```
