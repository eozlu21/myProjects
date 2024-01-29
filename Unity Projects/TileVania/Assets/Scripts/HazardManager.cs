using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HazardManager : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        GameObject otherGameObject = other.gameObject;
        if (!otherGameObject.tag.Equals("Player"))
        {
            return;
        }
        PlayerManager playerManager = otherGameObject.GetComponent<PlayerManager>();
        if (playerManager.isDead)
        {
            return;
        }
        playerManager.Die();
    }
}
