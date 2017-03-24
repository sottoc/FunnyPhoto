package com.km.funphotoeffects.gallery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.*;

import com.km.funphotoeffects.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.util.*;

public class GalleryView
{
    public class Album
    {
        public String albumId;
        private String albumName;
        private String imageUrl;
        private boolean selected;

        public String getAlbumName()
        {
            return albumName;
        }

        public String getImageUrl()
        {
            return imageUrl;
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setAlbumName(String s)
        {
            albumName = s;
        }

        public void setImageUrl(String s)
        {
            imageUrl = s;
        }

        public void setSelected(boolean flag)
        {
            selected = flag;
        }



        public Album()
        {
            super();
        }
    }

    public class AlbumAdapter extends BaseAdapter
    {
        Context mContext;
        LayoutInflater mInflater;
        ArrayList mList;

        public int getCount()
        {
            return mList.size();
        }

        public Album getItem(int i)
        {
            return (Album)mList.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            if(view == null)
            {
                view = mInflater.inflate(R.layout.row_album_item, null);
            }
            ViewHolder viewholder = (ViewHolder)view.getTag();
            if(viewholder == null)
            {
                viewholder = new ViewHolder(null);
                viewholder.linearLayout = (LinearLayout)view.findViewById(R.id.linearlayoutMain);
                viewholder.imageView = (ImageView)view.findViewById(R.id.imageView1);
                viewholder.textView = (TextView)view.findViewById(R.id.albumName);
                b = true;
            }
            if(((Album)mList.get(i)).isSelected())
            {
                viewholder.linearLayout.setBackgroundResource(R.drawable.border);
            } else
            {
                viewholder.linearLayout.setBackgroundColor(0);
            }
            if(b)
            {
                imageLoader.displayImage((new StringBuilder("file://")).append(((Album)mList.get(i)).imageUrl).toString(), viewholder.imageView, options, new SimpleImageLoadingListener() {
                    public void onLoadingComplete(String s, View view, Bitmap bitmap)
                    {
                        super.onLoadingComplete(s, view, bitmap);
                    }
                });
            }
            viewholder.textView.setText(((Album)mList.get(i)).albumName);
            view.setTag(viewholder);
            return view;
        }

        public AlbumAdapter(Context context, ArrayList arraylist)
        {
            super();
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mList = new ArrayList();
            mList = arraylist;
            notifyDataSetChanged();
        }
        
        private class ViewHolder
        {

            public ImageView imageView;
            public LinearLayout linearLayout;
            public TextView textView;

            private ViewHolder()
            {
                super();
            }

            ViewHolder(ViewHolder viewholder)
            {
                this();
            }
        }
    }

    public class GridAdapter extends BaseAdapter
    {

        android.widget.CompoundButton.OnCheckedChangeListener mCheckedChangeListener;
        Context mContext;
        LayoutInflater mInflater;
        ArrayList mList;
        SparseBooleanArray mSparseBooleanArray;

        public ArrayList getCheckedItems()
        {
            ArrayList arraylist = new ArrayList();
            int i = 0;
            do
            {
                if(i >= mList.size())
                {
                    return arraylist;
                }
                if(mSparseBooleanArray.get(i))
                {
                    arraylist.add((String)mList.get(i));
                }
                i++;
            } while(true);
        }

        public int getCount()
        {
            return imageUrls.size();
        }

        public Object getItem(int i)
        {
            return null;
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            if(view == null)
            {
                view = mInflater.inflate(R.layout.row_multiphoto_item, null);
            }
            ImageView imageview = (ImageView)view.findViewById(R.id.imageView1);
            imageLoader.displayImage((new StringBuilder("file://")).append((String)imageUrls.get(i)).toString(), imageview, options, new SimpleImageLoadingListener() {
                public void onLoadingComplete(String s, View view, Bitmap bitmap)
                {
                    super.onLoadingComplete(s, view, bitmap);
                }
            });
            return view;
        }

        public GridAdapter(Context context, ArrayList arraylist)
        {
            super();
            mCheckedChangeListener = new android.widget.CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton compoundbutton, boolean flag) 
				{
					mSparseBooleanArray.put(((Integer)compoundbutton.getTag()).intValue(), flag);
				}
			};
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mSparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList();
            mList = arraylist;
        }
    }

    public static interface SetImagePath
    {

        public abstract void setPath(String s);
    }


    private AlbumAdapter albumAdapter;
    private boolean b;
    private GridAdapter gridAdapter;
    protected ImageLoader imageLoader;
    private ArrayList imageUrls;
    private boolean isAlbumView;
    private ListView listView;
    private ArrayList mAlbumsList;
    private ArrayList mArrayUrl;
    private GridView mGridView;
    private LinearLayout mLinearBottom;
    private SetImagePath mSetImagePath;
    private LinearLayout mView;
    private DisplayImageOptions options;
    ArrayList selectedItems;
    private TextView textViewEmpty;

    public GalleryView(Context context, LinearLayout linearlayout, SetImagePath setimagepath)
    {
        mArrayUrl = new ArrayList();
        b = true;
        mView = linearlayout;
        mSetImagePath = setimagepath;
        fillAlbums();
        imageLoader = ImageLoader.getInstance();
        com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder = (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(0x108003f).showImageForEmptyUri(0x108003f);
        builder.considerExifParams(true);
        options = builder.build();
        albumAdapter = new AlbumAdapter(mView.getContext(), mAlbumsList);
        new ArrayList();
        listView = (ListView)mView.findViewById(R.id.listViewImage);
        mGridView = (GridView)mView.findViewById(R.id.gridGallery);
        textViewEmpty = (TextView)mView.findViewById(R.id.textview_no_gallery_image);
        listView.setAdapter(albumAdapter);
        listView.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
            public void onScroll(AbsListView abslistview, int i, int j, int k)
            {
            }

            public void onScrollStateChanged(AbsListView abslistview, int i)
            {
                b = true;
            }
        });
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                if(isAlbumView)
                {
                    refreshList(i);
                    fillGallery(((Album)mAlbumsList.get(i)).albumId);
                    b = false;
                }
            }
        });
        mGridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                String s = (String)imageUrls.get(i);
                mSetImagePath.setPath(s);
            }
        });
        if(mAlbumsList.size() > 0)
        {
            Collections.sort(mAlbumsList, new Comparator() {
                public int compare(Album album, Album album1)
                {
                    return album.getAlbumName().compareToIgnoreCase(album1.getAlbumName());
                }

                public int compare(Object obj, Object obj1)
                {
                    return compare((Album)obj, (Album)obj1);
                }
            });
        }
        fillAlbumFirst();
    }

    private void checkCorruptImages(Cursor cursor, int i)
    {
        if((double)(new File(cursor.getString(i))).length() != 0.0D)
        {
            imageUrls.add(cursor.getString(i));
        }
    }

    private void fillAlbumFirst()
    {
        int i;
        int j;
        i = -1;
        j = 0;
        if(mAlbumsList != null){
	        int k = 0;
	        while(k < mAlbumsList.size()){
	        	if(((Album)mAlbumsList.get(k)).getAlbumName().equalsIgnoreCase("camera"))
	            {
	        		i = k;
	        		break;
	            }
	        	if(getAlbumImageCount(((Album)mAlbumsList.get(k)).albumId) > j)
	            {
	                j = getAlbumImageCount(((Album)mAlbumsList.get(k)).albumId);
	                i = k;
	            }
	            k++;
	        }
        }
        if(i != -1 && listView != null)
        {
            final byte position = (byte)i;
            refreshList(i);
            fillGallery(((Album)mAlbumsList.get(i)).albumId);
            listView.post(new Runnable() {
                public void run()
                {
                    listView.smoothScrollToPosition(position);
                }
            });
        }
    }

    private void fillAlbums()
    {
        isAlbumView = true;
        mAlbumsList = new ArrayList();
        String as[] = {
            "_data", "_id", "bucket_display_name", "bucket_id"
        };
        Cursor cursor = mView.getContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, as, null, null, "datetaken DESC");
        HashSet hashset = new HashSet();
        int i = 0;
        do
        {
            if(i >= cursor.getCount())
            {
                return;
            }
            Album album = new Album();
            cursor.moveToPosition(i);
            int j = cursor.getColumnIndex("_data");
            int k = cursor.getColumnIndex("bucket_display_name");
            int l = cursor.getColumnIndex("bucket_id");
            cursor.getColumnIndex("_id");
            String s = cursor.getString(k);
            album.setAlbumName(s);
            album.setImageUrl(cursor.getString(j));
            album.albumId = cursor.getString(l);
            if(hashset.add(s))
            {
                mAlbumsList.add(album);
            }
            i++;
        } while(true);
    }

    private void fillGallery(String s)
    {
        String as[] = {
            "_data", "_id"
        };
        Cursor cursor = mView.getContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, as, "bucket_id=?", new String[] {
            s
        }, "datetaken DESC");
        imageUrls = new ArrayList();
        int i = 0;
        do
        {
            if(i >= cursor.getCount())
            {
                textViewEmpty.setVisibility(8);
                mGridView.setVisibility(0);
                gridAdapter = new GridAdapter(mView.getContext(), imageUrls);
                mGridView.setAdapter(gridAdapter);
                if(imageUrls == null || imageUrls.size() <= 0)
                {
                    textViewEmpty.setVisibility(0);
                    mGridView.setVisibility(4);
                }
                return;
            }
            cursor.moveToPosition(i);
            checkCorruptImages(cursor, cursor.getColumnIndex("_data"));
            i++;
        } while(true);
    }

    private int getAlbumImageCount(String s)
    {
        String as[] = {
            "_data", "_id"
        };
        Cursor cursor = mView.getContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, as, "bucket_id=?", new String[] {
            s
        }, "datetaken DESC");
        if(cursor != null)
        {
            return cursor.getCount();
        } else
        {
            return -1;
        }
    }

    private void refreshList(final int position)
    {
        if(mAlbumsList == null || mAlbumsList.size() <= 0)
        	return;
        int i = 0;
        while(i < mAlbumsList.size()){
        	((Album)mAlbumsList.get(i)).setSelected(false);
            i++;
        }
        ((Album)mAlbumsList.get(position)).setSelected(true);
        if(albumAdapter != null && listView != null)
        {
            albumAdapter.notifyDataSetChanged();
            listView.post(new Runnable() {
                public void run()
                {
                    listView.smoothScrollToPosition(position);
                }
            });
        }
    }

    public void onBackPressed()
    {
        isAlbumView = true;
        listView.setAdapter(albumAdapter);
    }

    public void onDone(View view)
    {
        if(gridAdapter == null)
        {
            Toast.makeText(mView.getContext(), "Select Photos from a Album first.", 1).show();
        }
    }

    public void refresh()
    {
        if(mArrayUrl != null)
        {
            mArrayUrl.clear();
        }
        if(mLinearBottom != null)
        {
            mLinearBottom.removeAllViews();
        }
    }

    public void setVisibility(boolean flag)
    {
        if(flag)
        {
            mView.setVisibility(0);
            return;
        } else
        {
            mView.setVisibility(8);
            return;
        }
    }
}
