package com.superlight.kashingmerchant.selector;

public interface OnSettingEventListener {

     void onBackViewPressed();
     void onLayoutPressed(int position);
     void onUsersListItemSelected(int position);
     void onPosPressed();
     void onBackUsersPressed();
     void onAddNewUserPressed();
     void onRootBackViewPressed();
     void onProductsListItemSelected(int position);
     void onBackProductsPressed();
     void onAddNewProductPressed();
     void onBackCategoriesPressed();
     void onAddNewCategoryPressed();
     void onCategoriesListItemSelected(int position);
     void onBackEditCategoryPressed();
     void onPickColorPressed();
     void onPickIconPressed();
}
